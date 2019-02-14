package com.jorado.lexicon.model;

/**
 * 词性单元
 * @author len
 */
public class DictUnit {
    private String word;
    private String tag;
    private int freq;

    public DictUnit(String word, String tag, int freq) {
        this.word = word;
        this.tag = tag;
        this.freq = freq;
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public int getFreq() {
        return freq;
    }

    public void setFreq(int freq) {
        this.freq = freq;
    }
}