package com.jorado.search.core.exception;


import com.jorado.logger.exception.CoreException;

/**
 * Created by Administrator on 15-5-20.
 * 没有可操作的数据
 */
public class NoDataFoundException extends CoreException {

    public NoDataFoundException() {
        super("No found any data!");
    }

    public NoDataFoundException(String message) {
        super(message);
    }
}
