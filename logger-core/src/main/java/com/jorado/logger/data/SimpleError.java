package com.jorado.logger.data;

public class SimpleError extends BaseData {

    private String message;
    private String type;
    private SimpleError inner;
    private String stackTrace;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }


    public SimpleError getInner() {
        return inner;
    }

    public void setInner(SimpleError inner) {
        this.inner = inner;
    }

    public String getStackTrace() {
        return stackTrace;
    }

    public void setStackTrace(String stackTrace) {
        this.stackTrace = stackTrace;
    }
}
