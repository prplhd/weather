package ru.prplhd.weather.config;

import com.zaxxer.hikari.HikariDataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;

import javax.sql.DataSource;

@Configuration
@PropertySource("classpath:app.properties")
public class DataSourceConfig {

    @Bean
    public DataSource dataSource(Environment env) {
        HikariDataSource ds = new HikariDataSource();

        String baseUrl = env.getRequiredProperty("db.url");
        String schema = env.getRequiredProperty("db.schema");
        String jdbcUrl = appendCurrentSchema(baseUrl, schema);

        ds.setJdbcUrl(jdbcUrl);
        ds.setUsername(env.getRequiredProperty("db.username"));
        ds.setPassword(env.getRequiredProperty("db.password"));
        ds.setDriverClassName(env.getRequiredProperty("db.driver"));

        int maxPoolSize = Integer.parseInt(env.getRequiredProperty("db.pool.maximum-pool-size"));
        ds.setMaximumPoolSize(maxPoolSize);

        int minIdle = Integer.parseInt(env.getRequiredProperty("db.pool.minimum-idle"));
        ds.setMinimumIdle(minIdle);

        long connTimeout = Long.parseLong(env.getRequiredProperty("db.pool.connection-timeout-ms"));
        ds.setConnectionTimeout(connTimeout);

        long idleTimeout = Long.parseLong(env.getRequiredProperty("db.pool.idle-timeout-ms"));
        ds.setIdleTimeout(idleTimeout);

        long maxLifetime = Long.parseLong(env.getRequiredProperty("db.pool.max-lifetime-ms"));
        ds.setMaxLifetime(maxLifetime);

        return ds;
    }

    private String appendCurrentSchema(String baseUrl, String schema) {
        String separator = baseUrl.contains("?") ? "&" : "?";
        return baseUrl + separator + "currentSchema=" + schema;
    }
}
