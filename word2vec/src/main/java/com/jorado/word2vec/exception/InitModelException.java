package com.jorado.word2vec.exception;


import com.jorado.logger.exception.CoreException;

/**
 * Created by Administrator on 15-5-20.
 */
public class InitModelException extends CoreException {

    public InitModelException(Throwable ex) {
        super("Init vector model error!!!", ex);
    }
}
