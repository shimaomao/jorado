package com.jorado.logger.exception;


/**
 * Created by Administrator on 15-5-20.
 */
public class NoListenerFoundException extends RuntimeException {

    public NoListenerFoundException() {
        this("No listener found,please register default listeners");
    }

    public NoListenerFoundException(String message) {
        super(message);
    }
}
