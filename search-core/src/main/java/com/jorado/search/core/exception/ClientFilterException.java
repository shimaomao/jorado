package com.jorado.search.core.exception;


import com.jorado.logger.exception.CoreException;

/**
 * Created by Administrator on 15-5-20.
 * 客户端过滤异常
 */
public class ClientFilterException extends CoreException {

    public ClientFilterException() {
        super("Invalid client,please verify the client has been authorized!");
    }

    public ClientFilterException(String message) {
        super(message);
    }
}
