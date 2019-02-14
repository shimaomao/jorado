package com.jorado.templateengine.exception;

/**
 * Created by Administrator on 15-5-20.
 */
public class ParsingException extends Exception {

    public ParsingException(Throwable ex) {
        super("Template parsing error!!!", ex);
    }
}
