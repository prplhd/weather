package ru.prplhd.weather.controller;

import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.prplhd.weather.dto.SignUpDto;
import ru.prplhd.weather.exception.LoginAlreadyExistsException;
import ru.prplhd.weather.service.AuthService;
import ru.prplhd.weather.util.SignUpDtoValidator;

@Controller
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;
    private final SignUpDtoValidator signUpDtoValidator;

    public AuthController(AuthService authService, SignUpDtoValidator signUpDtoValidator) {
        this.authService = authService;
        this.signUpDtoValidator = signUpDtoValidator;
    }

    @GetMapping("/sign-in")
    public String signInPage() {
        return "sign-in";
    }

    @GetMapping("/sign-up")
    public String signUpPage(@ModelAttribute("registrationDto") SignUpDto signUpDto) {
        return "sign-up";
    }

    @PostMapping("/sign-up")
    public String signUp (@ModelAttribute("registrationDto") @Valid SignUpDto signUpDto,
                          BindingResult bindingResult) {

        signUpDtoValidator.validate(signUpDto, bindingResult);

        if (bindingResult.hasErrors()) {
            return "sign-up";
        }

        try {
            authService.register(signUpDto);
        } catch (LoginAlreadyExistsException e) {
            bindingResult.rejectValue("login", "login.alreadyExists", "This login is already taken");
            return "sign-up";
        }

        return "redirect:/auth/sign-in";
    }
}
