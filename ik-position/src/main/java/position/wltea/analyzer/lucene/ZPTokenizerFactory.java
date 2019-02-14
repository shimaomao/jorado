package position.wltea.analyzer.lucene;

import org.apache.lucene.analysis.Tokenizer;
import org.apache.lucene.util.AttributeFactory;

import java.util.Map;

//solr7.*使用
public class ZPTokenizerFactory extends IKTokenizerFactory {

    private boolean useSmart;

    public ZPTokenizerFactory(Map<String, String> args) {
        super(args);
        this.useSmart = getBoolean(args, "useSmart", false);
    }

    public Tokenizer create(AttributeFactory factory) {
        return new ZPTokenizer(factory, this.useSmart);
    }
}