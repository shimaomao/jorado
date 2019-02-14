package com.jorado.word2vec.filter;

public class NullFilter implements Filter {
    @Override
    public String filter(String input) {
        return input;
    }
}
