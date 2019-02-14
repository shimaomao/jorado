package com.jorado.logger.data;

import com.jorado.logger.data.collection.StackFrameCollection;

public class InnerError extends BaseData {

    private String message;
    private String type;
    private String code;
    private InnerError inner;
    private StackFrameCollection stackTrace;
    private Method targetMethod;

    public InnerError(){
        stackTrace = new StackFrameCollection();
    }

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

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public InnerError getInner() {
        return inner;
    }

    public void setInner(InnerError inner) {
        this.inner = inner;
    }

    public StackFrameCollection getStackTrace() {
        return stackTrace;
    }

    public void setStackTrace(StackFrameCollection stackTrace) {
        this.stackTrace = stackTrace;
    }

    public Method getTargetMethod() {
        return targetMethod;
    }

    public void setTargetMethod(Method targetMethod) {
        this.targetMethod = targetMethod;
    }
}
