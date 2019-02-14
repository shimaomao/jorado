package com.jorado.search.core.exception;


import com.jorado.logger.exception.CoreException;

/**
 * Created by Administrator on 15-5-20.
 * 操作完成后异常
 */
public class ActionAfterException extends CoreException {

    public ActionAfterException(String message) {
        super(message);
    }
}
