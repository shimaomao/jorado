package com.jorado.fasttext.filter;

public class NullFilter implements Filter {
    @Override
    public String filter(String input) {
        return input;
    }
}
