package com.jorado.fasttext.filter;



public class CityFilter implements Filter {

    @Override
    public String filter(String input) {
        input = DictUtils.removeCity(input);
        return input;
    }
}
