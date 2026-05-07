package ru.prplhd.weather.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record RegistrationDto(
        @NotBlank(message = "Login is required")
        @Size(min = 5, max = 255, message = "Login must be between {min} and {max} characters")
        String login,

        @NotBlank(message = "Password is required")
        @Size(min = 6, max = 72, message = "Password must be between {min} and {max} characters")
        String password,

        @NotBlank(message = "Please repeat your password")
        @Size(min = 6, max = 72, message = "Password confirmation must be between {min} and {max} characters")
        String confirmPassword){
}
