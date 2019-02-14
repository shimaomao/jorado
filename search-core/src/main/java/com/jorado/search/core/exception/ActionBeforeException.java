package com.jorado.search.core.exception;


import com.jorado.logger.exception.CoreException;

/**
 * Created by Administrator on 15-5-20.
 * 操作执行前异常
 */
public class ActionBeforeException extends CoreException {

    public ActionBeforeException(String message) {
        super(message);
    }
}
