package ru.prplhd.weather.controller;

import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.prplhd.weather.dto.RegistrationDto;
import ru.prplhd.weather.service.AuthService;

@Controller
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @GetMapping("/sign-in")
    public String signInPage() {
        return "sign-in";
    }

    @GetMapping("/sign-up")
    public String signUpPage(@ModelAttribute("registrationDto") RegistrationDto registrationDto) {
        return "sign-up";
    }

    @PostMapping("/sign-up")
    public String signUp (@ModelAttribute("registrationDto") @Valid RegistrationDto registrationDto,
                          BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "sign-up";
        }

        authService.register(registrationDto);

        return "redirect:/auth/sign-in";
    }
}
