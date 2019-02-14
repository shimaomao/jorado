package com.jorado.fasttext.filter;

import com.jorado.logger.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class WordFilter implements Filter {

    private Pattern removedWordsPattern;
    private List<String> wordList;

    public WordFilter(String... words) {
        this.wordList = new ArrayList<>();
        for (String s : words) {
            this.wordList.add(s);
            this.removedWordsPattern = Pattern.compile(String.format("[%s]", StringUtils.joinString(this.wordList, "|")));
        }
    }

    /**
     * @param input
     * @return
     */
    @Override
    public String filter(String input) {
        input = removedWordsPattern.matcher(input).replaceAll("");
        return input;
    }
}
