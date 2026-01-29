package com.onlineCourse.eduhub.dto.auth;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class SignupRequest {

    @NotBlank
    @Size(min = 3, max = 200)
    private String name;

    @Email
    @NotBlank
    private String email;

    @NotBlank
    @Size(min = 5, max = 100)
    private String password;
}
