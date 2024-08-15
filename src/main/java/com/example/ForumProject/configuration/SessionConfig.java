package com.example.ForumProject.configuration;

import org.hibernate.sql.exec.spi.JdbcOperation;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.ConversionService;
import org.springframework.core.convert.support.GenericConversionService;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.session.SessionRepository;
import org.springframework.session.jdbc.JdbcIndexedSessionRepository;
import org.springframework.session.jdbc.config.annotation.web.http.EnableJdbcHttpSession;
import org.springframework.session.security.SpringSessionBackedSessionRegistry;
import org.springframework.session.web.context.AbstractHttpSessionApplicationInitializer;
import org.springframework.session.web.http.CookieHttpSessionIdResolver;
import org.springframework.session.web.http.HttpSessionIdResolver;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionManager;
import org.springframework.transaction.support.TransactionOperations;
import org.springframework.transaction.support.TransactionTemplate;

import javax.sql.DataSource;


@Configuration
@EnableJdbcHttpSession
public class SessionConfig extends AbstractHttpSessionApplicationInitializer {


//    @Bean
//    public SpringSessionBackedSessionRegistry sessionRegistry(SessionRepository sessionRepository) {
//        return new SpringSessionBackedSessionRegistry<>(sessionRepository);
//    }


    @Bean
    public JdbcIndexedSessionRepository sessionRepository(JdbcOperations jdbcOperations,
                                                          TransactionOperations transactionOperations) {

        JdbcIndexedSessionRepository repository = new JdbcIndexedSessionRepository(jdbcOperations,transactionOperations);
        repository.setDefaultMaxInactiveInterval(1800);  // 30 minutes

        // Register the custom converter

        SecurityContextConverter converter = new SecurityContextConverter();

        return repository;

//        return new JdbcIndexedSessionRepository(jdbcOperations, transactionOperations);

    }

//    @Bean
//    public JdbcIndexedSessionRepository sessionRepository(JdbcOperations dataSource) {
//        JdbcIndexedSessionRepository repository = new JdbcIndexedSessionRepository(dataSource);
//        repository.setDefaultMaxInactiveInterval(1800);  // 30 minutes
//
//        // Register the custom converter
//        GenericConversionService conversionService = (GenericConversionService) repository.getConversionService();
//        SecurityContextConverter converter = new SecurityContextConverter();
//        conversionService.addConverter(Object.class, byte[].class, converter);
//        conversionService.addConverter(byte[].class, Object.class, converter);
//
//        return repository;
//    }

    @Bean
    public HttpSessionIdResolver httpSessionIdResolver() {
        return new CookieHttpSessionIdResolver();
    }


    @Bean
    public JdbcOperations jdbcOperations(DataSource dataSource) {
        return new JdbcTemplate(dataSource);
    }

    @Bean
    public TransactionOperations transactionOperations(PlatformTransactionManager platformTransactionManager) {
        return new TransactionTemplate(platformTransactionManager);
    }

    @Bean
    public PlatformTransactionManager platformTransactionManager(DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }


}
