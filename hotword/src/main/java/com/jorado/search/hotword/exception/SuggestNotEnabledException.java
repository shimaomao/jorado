package com.jorado.search.hotword.exception;

/**
 * Created by Administrator on 15-5-20.
 */
public class SuggestNotEnabledException extends RuntimeException {

    public SuggestNotEnabledException(String message) {
        super(message);
    }

    public SuggestNotEnabledException() {
        this("热词提示服务没有启用");
    }
}
