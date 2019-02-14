package com.jorado.fasttext.filter;

@FunctionalInterface
public interface Filter {

    String filter(String input);
}
