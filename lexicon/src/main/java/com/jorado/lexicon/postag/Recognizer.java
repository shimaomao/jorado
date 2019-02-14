package com.jorado.lexicon.postag;

import com.jorado.lexicon.model.DictUnit;
import com.jorado.lexicon.model.NatureUnit;

import java.util.List;
import java.util.Set;

public interface Recognizer {

    /**
     * 词性识别
     *
     * @param content
     * @return
     */
    List<NatureUnit> recognition(String content);


    /**
     * 获取所有词性标签
     *
     * @return
     */
    Set<String> getTags();


    /**
     * 添加字典
     *
     * @param dictUnit
     */
    void addDict(DictUnit dictUnit);

}
