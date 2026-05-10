package ru.prplhd.weather.util;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ru.prplhd.weather.dto.RegistrationDto;
import ru.prplhd.weather.persistence.repository.UserRepository;

import java.util.Objects;

@Component
public class RegistrationDtoValidator implements Validator  {

    private final UserRepository userRepository;

    public RegistrationDtoValidator(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return RegistrationDto.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        RegistrationDto registrationDto = (RegistrationDto) target;

        validateLoginUniqueness(registrationDto, errors);
        validatePasswordMatch(registrationDto, errors);
    }

    private void validateLoginUniqueness(RegistrationDto registrationDto, Errors errors) {
        String login = registrationDto.login();

        if (errors.hasFieldErrors("login")) {
            return;
        }

        if (userRepository.existsByLogin(login)) {
            errors.rejectValue("login", "login.alreadyExists", "This login is already taken");
        }
    }

    private void validatePasswordMatch(RegistrationDto registrationDto, Errors errors) {
        String password = registrationDto.password();
        String confirmPassword = registrationDto.confirmPassword();

        if (errors.hasFieldErrors("password")) {
            return;
        }

        if (!Objects.equals(password, confirmPassword)) {
            errors.rejectValue("password", "password.mismatch", "Password confirmation does not match");
        }
    }
}
