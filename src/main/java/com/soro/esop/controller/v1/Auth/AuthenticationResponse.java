package com.soro.esop.controller.v1.Auth;

public class AuthenticationResponse {
    private final String responseMsg;

    public AuthenticationResponse(String responseMsg) {
        this.responseMsg = responseMsg;
    }

    public String getResponseMsg() {
        return responseMsg;
    }
}
