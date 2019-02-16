package com.jorado.qos.command;

public class NoSuchCommandException extends Exception {
    public NoSuchCommandException(String msg) {
        super("NoSuchCommandException:" + msg);
    }
}
