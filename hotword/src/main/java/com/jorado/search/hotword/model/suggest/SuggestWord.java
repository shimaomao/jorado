package com.jorado.search.hotword.model.suggest;

/*
 * Created by len.zhang on 2018/4/3.
 *
 */

import com.fasterxml.jackson.annotation.JsonProperty;

public class SuggestWord {
    @JsonProperty("w")
    String word;
    @JsonProperty("f")
    int flag;
    @JsonProperty("p")
    String payload;

    public SuggestWord(String word, int flag) {
        this.word = word;
        this.flag = flag;
    }


    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public int getFlag() {
        return flag;
    }

    public void setFlag(int flag) {
        this.flag = flag;
    }

    public String getPayload() {
        return payload;
    }

    public void setPayload(String payload) {
        this.payload = payload;
    }
}
