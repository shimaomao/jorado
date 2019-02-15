package com.jorado.word2vec.filter;


import com.jorado.dict.DictUtils;

public class CityFilter implements Filter {

    @Override
    public String filter(String input) {
        input = DictUtils.removeCity(input);
        return input;
    }
}
