package ru.prplhd.weather.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.transaction.annotation.Transactional;
import ru.prplhd.weather.config.AppConfig;
import ru.prplhd.weather.config.DataSourceConfig;
import ru.prplhd.weather.config.JpaConfig;
import ru.prplhd.weather.config.LiquibaseConfig;
import ru.prplhd.weather.dto.SignUpDto;
import ru.prplhd.weather.persistence.entity.UserEntity;
import ru.prplhd.weather.persistence.repository.SessionRepository;
import ru.prplhd.weather.persistence.repository.UserRepository;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringJUnitConfig(classes = {
        AppConfig.class,
        DataSourceConfig.class,
        JpaConfig.class,
        LiquibaseConfig.class
})
@TestPropertySource("classpath:app-test.properties")
@Transactional
class AuthServiceTest {

    @Autowired
    AuthService authService;

    @Autowired
    UserRepository userRepository;

    @Autowired
    SessionRepository sessionRepository;

    @Test
    @Tag("auth")
    @DisplayName("Sign up with valid data creates user in database")
    void whenSignUp_withValidDto_thenCreatesUser() {
        SignUpDto signUpDto = new SignUpDto("prplhd", "123qwe", "123qwe");

        authService.signUp(signUpDto);

        Optional<UserEntity> optUserEntity = userRepository.findByLoginIgnoreCase(signUpDto.login());

        assertThat(optUserEntity).isPresent();
    }
}
