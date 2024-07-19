package com.apiexamples.payload;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

public class RegistrationDto {

    private Long id;
    @Size(min = 2, max = 30, message = "Name should be 2 or more characters")
    private String name;
    @Email(message = "Should be a valid email address")
    private String email;
    @Size(min = 10, max = 10, message = "Mobile number should be 10 digits")
    private String mobile;

    private String message;

}