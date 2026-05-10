package ru.prplhd.weather.service;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.prplhd.weather.dto.SignUpDto;
import ru.prplhd.weather.exception.LoginAlreadyExistsException;
import ru.prplhd.weather.persistence.entity.UserEntity;
import ru.prplhd.weather.persistence.repository.UserRepository;
import ru.prplhd.weather.util.ConstraintViolationHandler;

@Service
@Transactional(readOnly = true)
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final ConstraintViolationHandler constraintViolationHandler;

    public AuthService(UserRepository userRepository,
                       PasswordEncoder passwordEncoder,
                       ConstraintViolationHandler constraintViolationHandler) {

        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.constraintViolationHandler = constraintViolationHandler;
    }

    @Transactional
    public void register(SignUpDto signUpDto) {
        String hashedPassword = passwordEncoder.encode(signUpDto.password());
        String login = signUpDto.login();

        UserEntity newUser = new UserEntity(login, hashedPassword);

        try {
            userRepository.saveAndFlush(newUser);

        } catch (DataIntegrityViolationException e) {
            if (constraintViolationHandler.isLoginUniqueConstraintViolation(e)) {
                throw new LoginAlreadyExistsException();
            }

            throw e;
        }
    }
}
