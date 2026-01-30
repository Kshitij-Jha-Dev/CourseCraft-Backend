package com.onlineCourse.eduhub.dto.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateProfileRequest {

    @Size(min = 3, max = 200, message = "Name must be between 3 and 200 characters")
    private String name;

    @Email(message = "Email should be valid")
    private String email;

    @Size(min = 5, message = "Password must be at least 5 and at most 100 characters")
    private String password;
    
    private String imageUrl;
}