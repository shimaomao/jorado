package com.jorado.search.hotword.model;

import com.jorado.logger.util.JsonUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * 热词基类
 */
public class Word {
    String id;
    String keyWord;
    int flag;
    int click;
    String pinYin;
    String py;
    List<String> similar;
    List<String> next;
    long rows;

    public Word(String keyWord) {
        this.keyWord=keyWord;
        this.similar = new ArrayList<>();
        this.next = new ArrayList<>();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getKeyWord() {
        return keyWord;
    }

    public void setKeyWord(String keyWord) {
        this.keyWord = keyWord;
    }

    public int getFlag() {
        return flag;
    }

    public void setFlag(int flag) {
        this.flag = flag;
    }

    public int getClick() {
        return click;
    }

    public void setClick(int click) {
        this.click = click;
    }

    public String getPinYin() {
        return pinYin;
    }

    public void setPinYin(String pinYin) {
        this.pinYin = pinYin;
    }

    public String getPy() {
        return py;
    }

    public void setPy(String py) {
        this.py = py;
    }

    public List<String> getSimilar() {
        return similar;
    }

    public void setSimilar(List<String> similar) {
        this.similar = similar;
    }

    public List<String> getNext() {
        return next;
    }

    public void setNext(List<String> next) {
        this.next = next;
    }

    public long getRows() {
        return rows;
    }

    public void setRows(long rows) {
        this.rows = rows;
    }

    @Override
    public String toString() {
        return JsonUtils.toJson(this);
    }
}
