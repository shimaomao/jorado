package com.jorado.ik.analysis;

import com.jorado.ik.ReloadableFilterFactory;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.util.CharArraySet;
import org.apache.lucene.analysis.util.ResourceLoader;

import java.io.IOException;
import java.util.Map;

public class DStopFilterFactory extends ReloadableFilterFactory {

    private CharArraySet stopWords;
    private boolean ignoreCase;
    //	private boolean enablePositionIncrements;

    public DStopFilterFactory(Map<String, String> args) {
        super(args);
        this.ignoreCase = getBoolean(args, "ignoreCase", false);
//		enablePositionIncrements = getBoolean(args, "enablePositionIncrements", false);
        System.out.println("construct:::::stop::::::::::::::::::::::" + conf);
    }

    @Override
    public TokenStream create(TokenStream arg0) {
        DStopFilter stopFilter = new DStopFilter(arg0, stopWords);
        return stopFilter;
    }

    @Override
    public void update(final ResourceLoader loader, final String paths) {
        try {

            stopWords = getWordSet(loader, paths, ignoreCase);

        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}
