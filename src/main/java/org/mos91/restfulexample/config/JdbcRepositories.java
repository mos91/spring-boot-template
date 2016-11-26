package org.mos91.restfulexample.config;

import org.mos91.restfulexample.repository.quotes.JdbcQuoteRepository;
import org.mos91.restfulexample.repository.quotes.QuoteRepository;
import org.mos91.restfulexample.repository.RepositoryType;
import org.mos91.restfulexample.repository.templates.jdbc.JdbcTemplatesRepository;
import org.mos91.restfulexample.repository.templates.TemplatesRepository;
import org.mos91.restfulexample.repository.templates.jdbc.TemplatesRepositoryQueries;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.transaction.support.TransactionTemplate;

import javax.sql.DataSource;
import java.io.IOException;

/**
 * Created by Oleg.Meleshin on 10/21/2016.
 */
@Configuration
@Profile("jdbc-repositories")
public class JdbcRepositories {

    @Bean
    @Qualifier("h2QuoteRepository")
    public QuoteRepository h2QuoteRepository(@Qualifier("h2JdbcTemplate") NamedParameterJdbcTemplate jdbcTemplate) {
        return new JdbcQuoteRepository(RepositoryType.H2, jdbcTemplate);
    }

    @Bean
    @Qualifier("pgQuoteRepository")
    public QuoteRepository pgQuoteRepository(@Qualifier("pgJdbcTemplate") NamedParameterJdbcTemplate jdbcTemplate) {
        return new JdbcQuoteRepository(RepositoryType.POSTGRES, jdbcTemplate);
    }

    @Bean
    @Qualifier("h2TemplateQueries")
    public TemplatesRepositoryQueries h2TemplateQueries() throws IOException {
        return new TemplatesRepositoryQueries("db/h2/queries/TemplateRepository.yaml");
    }

    @Bean
    @Qualifier("pgTemplateQueries")
    public TemplatesRepositoryQueries pgTemplateQueries() throws IOException {
        return new TemplatesRepositoryQueries("db/postgres/queries/TemplateRepository.yaml");
    }

    @Bean
    @Qualifier("h2TemplatesRepository")
    public TemplatesRepository h2TemplatesRepository(@Qualifier("h2JdbcTemplate") NamedParameterJdbcTemplate jdbcTemplate,
           @Qualifier("h2DataSource") DataSource dataSource,
           @Qualifier("h2TransactionTemplate") TransactionTemplate transactionTemplate,
           @Qualifier("h2TemplateQueries") TemplatesRepositoryQueries queries) {
        return new JdbcTemplatesRepository(RepositoryType.H2, dataSource, jdbcTemplate, transactionTemplate, queries);
    }

    @Bean
    @Qualifier("pgTemplatesRepository")
    public TemplatesRepository pgTemplatesRepository(@Qualifier("pgJdbcTemplate") NamedParameterJdbcTemplate jdbcTemplate,
           @Qualifier("pgDataSource") DataSource dataSource,
           @Qualifier("pgTransactionTemplate") TransactionTemplate transactionTemplate,
           @Qualifier("pgTemplateQueries") TemplatesRepositoryQueries queries) {
        return new JdbcTemplatesRepository(RepositoryType.POSTGRES, dataSource, jdbcTemplate, transactionTemplate, queries);
    }

}
