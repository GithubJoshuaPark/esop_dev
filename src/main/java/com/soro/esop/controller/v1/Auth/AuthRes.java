package com.soro.esop.controller.v1.Auth;

public class AuthRes {
    private final String responseMsg;

    public AuthRes(String responseMsg) {
        this.responseMsg = responseMsg;
    }

    public String getResponseMsg() {
        return responseMsg;
    }
}
