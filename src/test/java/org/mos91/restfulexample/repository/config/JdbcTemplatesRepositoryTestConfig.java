package org.mos91.restfulexample.repository.config;

import org.mos91.restfulexample.config.DBConfig;
import org.mos91.restfulexample.model.GreetingTemplate;
import org.mos91.restfulexample.repository.RepositoryType;
import org.mos91.restfulexample.repository.templates.TemplatesRepository;
import org.mos91.restfulexample.repository.templates.jdbc.JdbcTemplatesRepository;
import org.mos91.restfulexample.repository.templates.jdbc.TemplatesRepositoryQueries;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.transaction.support.TransactionTemplate;

import javax.sql.DataSource;
import java.io.IOException;

/**
 * Created by Oleg.Meleshin on 10/24/2016.
 */
@TestConfiguration
@Import({DBConfig.class})
public class JdbcTemplatesRepositoryTestConfig {

    @Bean
    TemplatesRepositoryQueries templateRepositoryQueries() throws IOException {
        return new TemplatesRepositoryQueries("db/h2/queries/TemplateRepository.yaml");
    }

    @Bean
    TemplatesRepositoryTestQueries templateRepositoryTestQueries() throws IOException {
        return new TemplatesRepositoryTestQueries("db/h2/queries/test/TemplateRepositoryTest.yaml");
    }

    @Bean
    TemplatesRepository templateRepository(NamedParameterJdbcTemplate jdbcTemplate,
        DataSource dataSource,
        TransactionTemplate transactionTemplate,
        TemplatesRepositoryQueries queries) {
        return new JdbcTemplatesRepository(RepositoryType.H2, dataSource, jdbcTemplate, transactionTemplate, queries);
    }

    @Bean
    GreetingTemplate template1() {
        return new GreetingTemplate(1L, "Hello, {0}");
    }

    @Bean
    GreetingTemplate template2() {
        return new GreetingTemplate(2L, "Привет, {0}");
    }

}
