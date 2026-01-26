package com.onlineCourse.eduhub.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Size(min = 3, max = 200, message = "Name must be between 3 and 200 characters")
    @Column(nullable = false)
    private String name;

    @NotBlank(message = "Email is mandatory")
    @Email(message = "Email should be valid")
    @Column(nullable = false, unique = true)
    private String email;

    @Builder.Default
    @Column(nullable = false)
    private String role = "ROLE_USER";

    @NotBlank(message = "Password cannot be empty")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @Column(nullable = false)
    private String password;
    
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private java.util.List<Enrollment> enrollments = new java.util.ArrayList<>();
}