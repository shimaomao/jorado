package com.jorado.fasttext.filter;

import com.jorado.lexicon.model.NatureUnit;
import com.jorado.lexicon.postag.PosTagger;
import com.jorado.lexicon.postag.Recognizer;
import com.jorado.lexicon.postag.impl.HanlpRecognizer;

import java.util.List;

public class SegmentFilter implements Filter {

    private PosTagger posTagger;

    public SegmentFilter() {
        this(new HanlpRecognizer());
    }

    public SegmentFilter(Recognizer recognizer) {
        posTagger = new PosTagger(recognizer);
    }

    @Override
    public String filter(String input) {
        List<NatureUnit> natureUnits = posTagger.recognition(input);
        StringBuilder sb = new StringBuilder();
        for (NatureUnit natureUnit : natureUnits) {
            String word = natureUnit.getWord();
            //剔除单个字
            if (word.length() > 1) {
                sb.append(natureUnit.getWord());
                sb.append(" ");
            }
        }
        return sb.toString();
    }
}
