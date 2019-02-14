package com.jorado.lexicon.postag.impl;

import com.jorado.lexicon.model.DictUnit;
import com.jorado.lexicon.model.NatureUnit;
import com.jorado.lexicon.postag.BaseRecognizer;
import com.jorado.logger.util.StringUtils;
import org.ansj.domain.Result;
import org.ansj.domain.Term;
import org.ansj.library.DicLibrary;
import org.ansj.recognition.Recognition;
import org.ansj.recognition.impl.DicRecognition;
import org.ansj.splitWord.analysis.DicAnalysis;

import java.util.ArrayList;
import java.util.List;

/**
 * 基于ansj的词性标注器
 */
public class AnsjRecognizer extends BaseRecognizer {

    private static final Recognition recognition;

    static {
        recognition = new DicRecognition();
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

        Result parseResult = DicAnalysis.parse(content);

        if (parseResult.size() < 1) return ret;

        Result result = parseResult.recognition(recognition);

        List<Term> terms = result.getTerms();

        for (Term term : terms) {
            // 当前词
            String name = term.getName();
            // 词性
            String tag = term.getNatureStr();
            String[] tags = StringUtils.splitString(tag, ",", true);
            for (String t : tags) {
                ret.add(new NatureUnit(name, t));
            }
        }
        return ret;
    }


    @Override
    public void add(DictUnit dictUnit) {
        DicLibrary.insert(DicLibrary.DEFAULT, dictUnit.getWord(), dictUnit.getTag(), dictUnit.getFreq());
    }
}
