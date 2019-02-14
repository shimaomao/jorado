package com.jorado.word2vec.filter;

@FunctionalInterface
public interface Filter {

    String filter(String input);
}
