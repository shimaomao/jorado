package com.jorado.fasttext.filter;

import com.jorado.basedata.BaseDataUtils;

public class CityFilter implements Filter {

    @Override
    public String filter(String input) {
        input = BaseDataUtils.removeCity(input);
        return input;
    }
}
