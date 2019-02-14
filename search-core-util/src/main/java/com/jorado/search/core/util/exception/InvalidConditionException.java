package com.jorado.search.core.util.exception;


/**
 * Created by Administrator on 15-5-20.
 */
public class InvalidConditionException extends CoreException {

    public InvalidConditionException() {
        super("Invalid condition,please verify the condition info!");
    }

    public InvalidConditionException(String message) {
        super(message);
    }
}
