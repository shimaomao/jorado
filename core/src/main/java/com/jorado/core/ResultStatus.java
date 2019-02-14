package com.jorado.core;

/**
 * @author len
 * @Description: 结果状态
 * @date 2018年6月28日
 */
public enum ResultStatus {

    OK(200, "OK"),

    BAD_REQUEST(400, "Bad Request"),

    UNAUTHORIZED(401, "Unauthorized"),

    FORBIDDEN(403, "Forbidden"),

    NOT_FOUND(404, "Data Not Found"),

    ERROR(500, "Internal Error"),

    NOT_SUPPORTED(505, "Not Supported"),

    CONFIG_ERROR(601, "Config Error"),

    THIRD_PARTY_API_ERROR(9000, "Third Party Api Error"),

    RETRY_SEND_MESSAGE(5555, "Retry Send Message");

    /**
     * 状态码
     */
    private Integer code;

    /**
     * 状态描述
     */
    private String message;

    ResultStatus(Integer code, String message) {
        this.code = code;
        this.message = message;
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
}
