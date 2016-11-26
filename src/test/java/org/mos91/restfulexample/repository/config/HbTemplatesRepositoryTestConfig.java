package org.mos91.restfulexample.repository.config;

import org.hibernate.SessionFactory;
import org.mos91.restfulexample.config.DBConfig;
import org.mos91.restfulexample.model.GreetingTemplate;
import org.mos91.restfulexample.repository.RepositoryType;
import org.mos91.restfulexample.repository.templates.TemplatesRepository;
import org.mos91.restfulexample.repository.templates.hb.HibernateTemplatesRepository;
import org.mos91.restfulexample.repository.templates.hb.TemplatesRepositoryQueries;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;
import java.io.IOException;

import static org.mos91.restfulexample.utils.Utils.createSessionFactory;

/**
 * Created by Oleg.Meleshin on 11/18/2016.
 */
@TestConfiguration
@Import({DBConfig.class})
public class HbTemplatesRepositoryTestConfig {

    @Bean
    public SessionFactory sessionFactory(DataSource dataSource) throws IOException {
        return createSessionFactory(dataSource);
    }

    @Bean
    public PlatformTransactionManager transactionManager(DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }

    @Bean
    public TemplatesRepositoryQueries queries() throws IOException {
        return new TemplatesRepositoryQueries("db/hql/queries/TemplateRepository.yaml");
    }

    @Bean
    public TemplatesRepository templatesRepository(SessionFactory sessionFactory,
        TemplatesRepositoryQueries queries) {
        return new HibernateTemplatesRepository(RepositoryType.H2, sessionFactory, queries);
    }

    @Bean
    TemplatesRepositoryTestQueries templateRepositoryTestQueries() throws IOException {
        return new TemplatesRepositoryTestQueries("db/h2/queries/test/TemplateRepositoryTest.yaml");
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
