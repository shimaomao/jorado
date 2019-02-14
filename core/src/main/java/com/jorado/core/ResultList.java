package com.jorado.core;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 基于集合的操作结果集
 *
 * @param <T>
 */
public class ResultList<T> extends Result<List<T>> implements Serializable {

    public ResultList() {
        this(new ArrayList<>());
    }

    public ResultList(List<T> data) {
        super(data);
    }

    public ResultList(int code, String message) {
        super(code, message);
    }

    public ResultList(int code, String message, List<T> data) {
        super(code, message, data);
    }

    public ResultList(ResultStatus status) {
        super(status);
    }

    public ResultList(ResultStatus status, String message) {
        super(status, message);
    }

    public ResultList(ResultStatus status, List<T> data) {
        super(status, data);
    }

    public ResultList(ResultStatus status, String message, List<T> data) {
        super(status, message, data);
    }

    public ResultList(List<T> data, ResultStatus status) {
        super(status, data);
    }

    public ResultList(List<T> data, ResultStatus status, String message) {
        super(status, message, data);
    }
}
