package com.soro.esop.model;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {
    private Long id;


    @NotNull    
    @Size(min = 4, max = 50, message = "Username must be between 4 and 50 characters")    
    private String username;

    // @NotNull
    // @Size(min = 4, max = 50, message = "Email must be between 4 and 50 characters")
    // @Pattern(regexp = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$", 
    //          message = "Email must be valid")
    //private String email;

    // @NotNull
    // @Size(min = 4, max = 50, message = "Password must be between 4 and 50 characters")
    // @Pattern(regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{4,}$", 
    //          message = "Password must contain at least one digit, one lowercase, one uppercase, one special character, and no whitespace")
    private String password;

    private Boolean enabled = true;
}
