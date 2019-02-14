package com.jorado.search.hotword.model.suggest;

import com.jorado.search.hotword.model.enums.HotWordType;

import java.util.ArrayList;
import java.util.List;

/*
 * Created by len.zhang on 2018/4/17.
 *
 */

public class SuggestResult {

    private String keyWord;
    private int rows;
    private HotWordType flag;
    private List<SuggestWord> words;
    private List<SuggestWord> suggests;
    private long numFound;

    public SuggestResult(String keyWord, int rows, HotWordType flag) {
        this.keyWord = keyWord;
        this.rows = rows;
        this.flag = flag;
        this.words = new ArrayList<>();
        this.suggests = new ArrayList<>();
    }

    public String getKeyWord() {
        return keyWord;
    }

    public void setKeyWord(String keyWord) {
        this.keyWord = keyWord;
    }

    public int getRows() {
        return rows;
    }

    public void setRows(int rows) {
        this.rows = rows;
    }

    public HotWordType getFlag() {
        return flag;
    }

    public void setFlag(HotWordType flag) {
        this.flag = flag;
    }

    public List<SuggestWord> getWords() {
        return words;
    }

    public void setWords(List<SuggestWord> words) {
        this.words = words;
    }

    public List<SuggestWord> getSuggests() {
        return suggests;
    }

    public void setSuggests(List<SuggestWord> suggests) {
        this.suggests = suggests;
    }

    public long getNumFound() {
        return numFound;
    }

    public void setNumFound(long numFound) {
        this.numFound = numFound;
    }
}
