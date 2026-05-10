package ru.prplhd.weather.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.Clock;
import java.time.Duration;

@Configuration
@PropertySource("classpath:app.properties")
@ComponentScan({
        "ru.prplhd.weather.service",
        "ru.prplhd.weather.util"
})
public class AppConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public Clock clock() {
        return Clock.systemUTC();
    }

    @Bean
    public Duration sessionTtl(Environment env) {
        long ttlHours = env.getRequiredProperty("app.session.ttl-hours", Long.class);

        return Duration.ofHours(ttlHours);
    }
}
