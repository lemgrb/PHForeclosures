package com.phforeclosures.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.relational.core.dialect.AnsiDialect;
import org.springframework.data.relational.core.dialect.Dialect;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;

@Configuration
public class JdbcConfig {

    @Bean
    public Dialect jdbcDialect(NamedParameterJdbcOperations operations) {
        return AnsiDialect.INSTANCE;
    }
}
