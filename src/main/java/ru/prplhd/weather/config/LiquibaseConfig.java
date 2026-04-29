package ru.prplhd.weather.config;

import liquibase.integration.spring.SpringLiquibase;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;

import javax.sql.DataSource;

@Configuration
@PropertySource("classpath:app.properties")
public class LiquibaseConfig {

    @Bean
    public SpringLiquibase liquibase(DataSource dataSource, Environment env) {
        SpringLiquibase liquibase = new SpringLiquibase();
        liquibase.setDataSource(dataSource);
        liquibase.setChangeLog(env.getRequiredProperty("liquibase.change-log"));
        liquibase.setDefaultSchema(env.getRequiredProperty("liquibase.default-schema"));
        liquibase.setLiquibaseSchema(env.getRequiredProperty("liquibase.liquibase-schema"));
        return liquibase;
    }
}
