package com.jorado.lexicon.postag;

import com.jorado.lexicon.model.DictUnit;
import com.jorado.logger.util.StringUtils;

import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 资源加载器
 */
public abstract class BaseRecognizer implements Recognizer {

    /**
     * 用于存储所有词性标签(线程安全)
     */
    private final Set<String> tags = ConcurrentHashMap.newKeySet();

    /**
     * 加载词典
     *
     * @param dictUnit
     */
    public void addDict(DictUnit dictUnit) {
        this.add(dictUnit);
        String[] tags = StringUtils.splitString(dictUnit.getTag(), ",", true);
        for (String t : tags) {
            this.tags.add(t);
        }

    }

    /**
     * 获取所有词性标签
     *
     * @return
     */
    public Set<String> getTags() {
        return tags;
    }

    /**
     * 添加单元字典
     *
     * @param dictUnit
     */
    protected abstract void add(DictUnit dictUnit);
}
