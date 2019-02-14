package com.jorado.logger.exception;


/**
 * Created by Administrator on 15-5-20.
 */
public class NullEventException extends NullPointerException {

    public NullEventException() {
        this("Event is null");
    }

    public NullEventException(String message) {
        super(message);
    }
}
