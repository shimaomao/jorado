package com.jorado.lexicon.postag.impl;

import com.hankcs.hanlp.dictionary.CustomDictionary;
import com.hankcs.hanlp.seg.common.Term;
import com.hankcs.hanlp.tokenizer.NotionalTokenizer;
import com.jorado.lexicon.model.DictUnit;
import com.jorado.lexicon.model.NatureUnit;
import com.jorado.lexicon.postag.BaseRecognizer;

import java.util.ArrayList;
import java.util.List;

/**
 * 基于hanlp的词性标注器
 */
public class HanlpRecognizer extends BaseRecognizer {

    static {
        NotionalTokenizer.SEGMENT.enablePartOfSpeechTagging(true);
    }

    /**
     * 自定义分词
     *
     * @param content 待标注内容
     * @return
     */
    @Override
    public List<NatureUnit> recognition(String content) {

        List<NatureUnit> ret = new ArrayList<>();

        List<Term> terms = NotionalTokenizer.segment(content);

        for (Term term : terms) {
            ret.add(new NatureUnit(term.word, term.nature.toString()));
        }
        return ret;
    }

    @Override
    protected void add(DictUnit unit) {
        CustomDictionary.add(unit.getWord(), String.format("%s %s", unit.getTag(), unit.getFreq()));
    }
}
