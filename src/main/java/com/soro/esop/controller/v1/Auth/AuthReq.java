package com.soro.esop.controller.v1.Auth;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class AuthReq {
    @NotBlank
    private String username;
    @NotBlank
    private String password;
}
