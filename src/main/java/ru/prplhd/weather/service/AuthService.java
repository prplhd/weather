package ru.prplhd.weather.service;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.prplhd.weather.dto.SignInDto;
import ru.prplhd.weather.dto.SignUpDto;
import ru.prplhd.weather.exception.InvalidCredentialsException;
import ru.prplhd.weather.exception.LoginAlreadyExistsException;
import ru.prplhd.weather.persistence.entity.SessionEntity;
import ru.prplhd.weather.persistence.entity.UserEntity;
import ru.prplhd.weather.persistence.repository.SessionRepository;
import ru.prplhd.weather.persistence.repository.UserRepository;
import ru.prplhd.weather.util.ConstraintViolationHandler;

import java.time.Clock;
import java.time.Duration;
import java.time.Instant;
import java.util.UUID;

@Service
@Transactional(readOnly = true)
public class AuthService {

    private static final Duration SESSION_TTL = Duration.ofHours(24);

    private final UserRepository userRepository;
    private final SessionRepository sessionRepository;

    private final PasswordEncoder passwordEncoder;
    private final ConstraintViolationHandler constraintViolationHandler;
    private final Clock clock;
    private final Duration sessionTtl;

    public AuthService(UserRepository userRepository, SessionRepository sessionRepository,
                       PasswordEncoder passwordEncoder,
                       ConstraintViolationHandler constraintViolationHandler, Clock clock, Duration sessionTtl) {

        this.userRepository = userRepository;
        this.sessionRepository = sessionRepository;

        this.passwordEncoder = passwordEncoder;
        this.constraintViolationHandler = constraintViolationHandler;
        this.clock = clock;
        this.sessionTtl = sessionTtl;
    }

    @Transactional
    public void signUp(SignUpDto signUpDto) {
        String hashedPassword = passwordEncoder.encode(signUpDto.password());
        String login = signUpDto.login();

        UserEntity newUser = new UserEntity(login, hashedPassword);

        try {
            userRepository.saveAndFlush(newUser);

        } catch (DataIntegrityViolationException e) {
            if (constraintViolationHandler.isLoginUniqueConstraintViolation(e)) {
                throw new LoginAlreadyExistsException("This login is already taken");
            }

            throw e;
        }
    }

    @Transactional
    public UUID signIn(SignInDto signInDto) {
        String login = signInDto.login();
        String rawPassword = signInDto.password();

        UserEntity userEntity = userRepository.findByLoginIgnoreCase(login)
                .orElseThrow(() -> new InvalidCredentialsException("Invalid login or password"));

        String passwordHash = userEntity.getPasswordHash();
        boolean passwordsMatches = passwordEncoder.matches(rawPassword, passwordHash);

        if (!passwordsMatches) {
            throw new InvalidCredentialsException("Invalid login or password");
        }

        UUID sessionId = UUID.randomUUID();
        Instant createdAt = clock.instant();
        Instant expiresAt = createdAt.plus(sessionTtl);

        SessionEntity sessionEntity = new SessionEntity(
                sessionId,
                userEntity,
                expiresAt
        );

        sessionRepository.save(sessionEntity);

        return sessionId;
    }
}
