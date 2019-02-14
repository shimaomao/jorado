package com.jorado.ik.admin.model;

/**
 * Created by len.zhang on 2018/9/17.
 */
public class SegWord {

    private String term;
    private int frequency;

    public SegWord() {
    }

    public SegWord(String term, int frequency) {
        this.term = term;
        this.frequency = frequency;
    }

    public String getTerm() {
        return term;
    }

    public void setTerm(String term) {
        this.term = term;
    }

    public int getFrequency() {
        return frequency;
    }

    public void setFrequency(int frequency) {
        this.frequency = frequency;
    }
}
