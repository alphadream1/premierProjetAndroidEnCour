package com.ferraris.premierprojetcourandroid.model.beans;

public class ErrorBean {

    private String message;
    private String code;

    public ErrorBean() {
    }

    public ErrorBean(String message, String code) {
        this.message = message;
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
