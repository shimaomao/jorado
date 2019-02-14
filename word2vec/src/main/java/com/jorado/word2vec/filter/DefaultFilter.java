package com.jorado.word2vec.filter;

import com.jorado.logger.util.StringUtils;

public class DefaultFilter implements Filter {

    @Override
    public String filter(String input) {
        input = input.toLowerCase();
        input = StringUtils.removeHTMLTag(input);
        input = input.replaceAll("/", " ").replaceAll("\\[.*\\]", "").replaceAll("\\(.*\\)", "").replaceAll("\\（.*\\）", "").replaceAll("\\【.*\\】", "");
        input = StringUtils.removeNumber(input);
        input = StringUtils.removeSpecialChar(input);
        return input;
    }
}
