package com.jorado.core.cache;

public class ExpiryException extends Exception {

    public ExpiryException() {
        super("缓存已经过期");
    }

    public ExpiryException(String message) {
        super(message); // 调用超类构造器
    }
}
