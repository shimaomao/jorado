package com.jorado.ik.analysis;

import com.jorado.ik.ReloadableFilterFactory;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.core.LowerCaseFilter;
import org.apache.lucene.analysis.core.WhitespaceTokenizer;
import org.apache.lucene.analysis.synonym.SolrSynonymParser;
import org.apache.lucene.analysis.synonym.SynonymFilter;
import org.apache.lucene.analysis.synonym.SynonymMap;
import org.apache.lucene.analysis.util.ResourceLoader;

import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.CodingErrorAction;
import java.text.ParseException;
import java.util.List;
import java.util.Map;

public class DSynonymFilterFactory extends ReloadableFilterFactory {

    private SynonymMap map; // 词库，可以通过引用改变
    private boolean ignoreCase; //属性
    private boolean expand;

    public DSynonymFilterFactory(Map<String, String> args) {
        super(args);
        expand = getBoolean(args, "expand", true);
        ignoreCase = getBoolean(args, "ignoreCase", false);
    }

    @Override
    public TokenStream create(TokenStream input) {
        return map.fst == null ? input : new SynonymFilter(input, map, ignoreCase);
    }

    @Override
    public void update(final ResourceLoader loader, final String paths) {

        try {
            map = loadSolrSynonyms(loader, paths); // 内部已实现切换
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }

    }

    private SynonymMap loadSolrSynonyms(ResourceLoader loader, String paths) throws IOException, ParseException {
        final Analyzer analyzer = new Analyzer() {
            @Override
            protected TokenStreamComponents createComponents(String fieldName) {
                WhitespaceTokenizer tokenizer = new WhitespaceTokenizer();
                TokenStream stream = ignoreCase ? new LowerCaseFilter(tokenizer) : tokenizer;
                return new TokenStreamComponents(tokenizer, stream);
            }
        };

        String synonyms = paths;

        CharsetDecoder decoder = Charset.forName("UTF-8").newDecoder()
                .onMalformedInput(CodingErrorAction.REPORT)
                .onUnmappableCharacter(CodingErrorAction.REPORT);

        SolrSynonymParser parser = new SolrSynonymParser(true, expand, analyzer);
        File synonymFile = new File(synonyms);
        if (loader != null) { //first call in constructor
            if (synonymFile.exists()) {
                decoder.reset();
                //4.6以上版本
                parser.parse(new InputStreamReader(loader.openResource(synonyms),
                        decoder));
            } else {
                List<String> files = splitFileNames(synonyms);
                for (String file : files) {
                    decoder.reset();
//                  4.6以上版本
                    parser.parse(new InputStreamReader(loader.openResource(file),
                            decoder));
                }
            }
        }

        return parser.build();
    }
}
