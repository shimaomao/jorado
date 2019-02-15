package com.jorado.core;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @param <T>
 * @author len
 */
public class Result<T> implements Serializable {

    public static final Result OK = new Result(ResultStatus.OK);
    public static final Result BAD_REQUEST = new Result(ResultStatus.BAD_REQUEST);
    public static final Result UNAUTHORIZED = new Result(ResultStatus.UNAUTHORIZED);
    public static final Result FORBIDDEN = new Result(ResultStatus.FORBIDDEN);
    public static final Result NOT_FOUND = new Result(ResultStatus.NOT_FOUND);
    public static final Result ERROR = new Result(ResultStatus.ERROR);
    public static final Result NOT_SUPPORTED = new Result(ResultStatus.NOT_SUPPORTED);
    public static final Result CONFIG_ERROR = new Result(ResultStatus.CONFIG_ERROR);
    public static final Result THIRD_PARTY_API_ERROR = new Result(ResultStatus.THIRD_PARTY_API_ERROR);

    private Integer code = ResultStatus.OK.getCode();
    private String message = ResultStatus.OK.getMessage();
    private List debugInfo = new ArrayList<>();
    private T data;

    /**
     * 无参数构造函数是必须的
     */
    public Result() {

    }

    public Result(T data) {
        this.data = data;
    }

    public Result(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public Result(int code, String message, T data) {
        this(code, message);
        this.data = data;
    }

    public Result(ResultStatus resultStatus) {
        this(resultStatus.getCode(), resultStatus.getMessage());
    }

    public Result(ResultStatus resultStatus, String message) {
        this(resultStatus.getCode(), message);
    }

    public Result(ResultStatus resultStatus, T data) {
        this(resultStatus.getCode(), resultStatus.getMessage(), data);
    }

    public Result(ResultStatus resultStatus, String message, T data) {
        this(resultStatus.getCode(), message, data);
    }

    public Result(T data, int code, String message) {
        this(code, message, data);
    }

    public Result(T data, ResultStatus resultStatus) {
        this(resultStatus, data);
    }

    public Result(T data, ResultStatus resultStatus, String message) {
        this(resultStatus, message, data);
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public List getDebugInfo() {
        return debugInfo;
    }

    public void setDebugInfo(List debugInfo) {
        this.debugInfo = debugInfo;
    }

    public boolean isOk() {
        return code == 200;
    }

    public boolean isFail() {
        return !isOk();
    }

    public static <T> Result<T> ofSuccess(T data) {
        Result<T> result = new Result<>();
        result.setCode(200);
        result.setData(data);
        return result;
    }

    public static <T> Result<T> ofSuccess(T data, String message) {
        Result<T> result = new Result<>();
        result.setCode(200);
        result.setData(data);
        result.setMessage(message);
        return result;
    }

    public static <T> Result<T> ofSuccess(String message) {
        Result<T> result = new Result<>();
        result.setCode(200);
        result.setMessage(message);
        return result;
    }

    public static <T> Result<T> ofFail(String message) {
        return ofFail(500, message);
    }

    public static <T> Result<T> ofFail(int code, String message) {
        Result<T> result = new Result<>();
        result.setCode(code);
        result.setMessage(message);
        return result;
    }

    public static <T> Result<T> ofThrowable(Throwable throwable) {
        return ofThrowable(500, throwable);
    }

    public static <T> Result<T> ofThrowable(int code, Throwable throwable) {
        Result<T> result = new Result<>();
        result.setCode(code);
        result.setMessage(throwable.getClass().getName() + ", " + throwable.getMessage());
        return result;
    }

    @Override
    public String toString() {
        return "Result{" +
                "code=" + code +
                ", message='" + message + '\'' +
                ", data=" + data +
                ", debugInfo=" + debugInfo +
                '}';
    }
}
