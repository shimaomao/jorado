package com.jorado.search.core.util.condition;

import com.jorado.search.core.util.Constants;
import com.jorado.search.core.util.enums.QueryMode;
import com.jorado.search.core.util.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * 关键字查询条件
 */
public class KeywordCondition extends Condition  {

    private String word;
    private int weight = -1;
    private int step = -1;
    private String segment;

    public KeywordCondition(String field, String word, int weight, int step, String segment) {
        super(field);
        this.word = word;
        this.weight = weight;
        this.step = step;
        this.segment = segment;
    }

    public KeywordCondition(String field, String word) {
        this(field, word, -1, -1, null);
    }

    @Override
    public QueryMode getQueryMode() {
        return QueryMode.KEYWORD;
    }

    @Override
    public String getValue() {
        return String.format("%s|%d|%d|%s", this.word, this.weight, this.step, this.segment);
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public int getStep() {
        return step;
    }

    public void setStep(int step) {
        this.step = step;
    }

    public String getSegment() {
        return segment;
    }

    public void setSegment(String segment) {
        this.segment = segment;
    }
}
