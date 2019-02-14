package org.wltea.analyzer;

import org.apache.lucene.analysis.util.ResourceLoader;
import org.junit.Test;
import org.wltea.analyzer.core.IKSegmenter;
import org.wltea.analyzer.core.Lexeme;
import org.wltea.analyzer.lucene.IKTokenizerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import java.util.HashMap;
import java.util.Map;

public class TestIk {

    @Test
    public void test() throws IOException {
        IKSegmenter ik = new IKSegmenter(new StringReader("ansj_seg,(ansj中文分词)在这里如果你遇到什么问题都可以联系我.我一定尽我所能.帮助大家.ansj_seg更快,更准,更自由"), false);
        Lexeme lexeme;
        while (null != (lexeme = ik.next())) {
            System.out.println(lexeme);
        }
    }

    @Test
    public void test1() {

        Map<String, String> params = new HashMap<>();
        params.put("useSmart", "true");
        params.put("conf", "company.conf");
        IKTokenizerFactory factory = new IKTokenizerFactory(params);
        factory.inform(new ResourceLoader() {
            @Override
            public InputStream openResource(String resource) throws IOException {
                return null;
            }

            @Override
            public <T> Class<? extends T> findClass(String cname, Class<T> expectedType) {
                return null;
            }

            @Override
            public <T> T newInstance(String cname, Class<T> expectedType) {
                return null;
            }
        });
    }
}
