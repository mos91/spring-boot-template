package org.mos91.restfulexample.config;

import org.apache.commons.dbcp.BasicDataSource;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.support.TransactionTemplate;
import org.springframework.web.client.RestTemplate;

import javax.sql.DataSource;

import java.io.IOException;

import static org.mos91.restfulexample.utils.Utils.createSessionFactory;

/**
 * Created by Oleg.Meleshin on 10/21/2016.
 */
@Configuration
public class Config {

    @Autowired
    private Environment env;

    @Bean
    public RestTemplate restTemplate(RestTemplateBuilder builder) {
        return builder.build();
    }

    @Bean
    @Qualifier("pgDataSource")
    public DataSource pgDataSource() {
        DataSourceBuilder builder = DataSourceBuilder.create();

        return builder.type(BasicDataSource.class)
                .driverClassName(env.getProperty("pg.datasource.driver-class-name"))
                .url(env.getProperty("pg.datasource.url"))
                .username(env.getProperty("pg.datasource.username"))
                .password(env.getProperty("pg.datasource.password")).build();
    }

    @Bean
    @Qualifier("h2DataSource")
    public DataSource h2DataSource() {
        DataSourceBuilder builder = DataSourceBuilder.create();

        return builder.type(BasicDataSource.class)
                .driverClassName(env.getProperty("h2.datasource.driver-class-name"))
                .url(env.getProperty("h2.datasource.url"))
                .username(env.getProperty("h2.datasource.username"))
                .password(env.getProperty("h2.datasource.password")).build();
    }

    @Bean
    @Qualifier("pgTransactionManager")
    public PlatformTransactionManager pgTransactionManager(@Qualifier("pgDataSource") DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }

    @Bean
    @Qualifier("h2TransactionManager")
    public PlatformTransactionManager h2TransactionManager(@Qualifier("h2DataSource") DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }

    @Bean
    @Qualifier("pgTransactionTemplate")
    public TransactionTemplate pgTransactionTemplate(@Qualifier("pgTransactionManager") PlatformTransactionManager txManager) {
        return new TransactionTemplate(txManager);
    }

    @Bean
    @Qualifier("h2TransactionTemplate")
    public TransactionTemplate h2TransactionTemplate(@Qualifier("h2TransactionManager") PlatformTransactionManager txManager) {
        return new TransactionTemplate(txManager);
    }

    @Bean
    @Qualifier("pgJdbcTemplate")
    public NamedParameterJdbcTemplate pgJdbcTemplate(@Qualifier("pgDataSource") DataSource dataSource) {
        return new NamedParameterJdbcTemplate(dataSource);
    }

    @Bean
    @Qualifier("h2JdbcTemplate")
    public NamedParameterJdbcTemplate h2JdbcTemplate(@Qualifier("h2DataSource") DataSource dataSource) {
        return new NamedParameterJdbcTemplate(dataSource);
    }

    @Bean
    @Qualifier("pgSessionFactory")
    public SessionFactory pgSessionFactory(@Qualifier("pgDataSource") DataSource dataSource) throws IOException {
        return createSessionFactory(dataSource);
    }

    @Bean
    @Qualifier("h2SessionFactory")
    public SessionFactory h2SessionFactory(@Qualifier("h2DataSource") DataSource dataSource) throws IOException {
        return createSessionFactory(dataSource);
    }

}
