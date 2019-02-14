package com.jorado.templateengine.exception;

public class EngineInitException extends Exception {
    public EngineInitException(Throwable ex) {
        super("Template engine init error!!!", ex);
    }
}
