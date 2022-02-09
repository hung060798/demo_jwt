package com.example.demojwt.dto.response;

public class MessageResponse {
    private String message;
    private int code;
    private Object data;

    public MessageResponse() {
    }

    public MessageResponse(String message, int code, Object data) {
        this.message = message;
        this.code = code;
        this.data = data;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public MessageResponse(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
