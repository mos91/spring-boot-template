package org.mos91.restfulexample;

import org.mos91.restfulexample.utils.Utils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.data.jpa.JpaRepositoriesAutoConfiguration;
import org.springframework.boot.autoconfigure.flyway.FlywayAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.JdbcTemplateAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;

import java.io.IOException;
import java.text.MessageFormat;

@SpringBootApplication
@EnableScheduling
@EnableConfigurationProperties
@ComponentScan(basePackages = "org.mos91.restfulexample")
@EnableAutoConfiguration(exclude = {JpaRepositoriesAutoConfiguration.class, JdbcTemplateAutoConfiguration.class,
       DataSourceAutoConfiguration.class, HibernateJpaAutoConfiguration.class, FlywayAutoConfiguration.class})
public class SpringRestfulWebserviceApplication {

    private static final Logger LOG = LoggerFactory.getLogger(SpringRestfulWebserviceApplication.class);

    public static void main(String[] args) {
        SpringRestfulWebserviceApplication application = new SpringRestfulWebserviceApplication();
        application.start(args);
    }

    private void start(String[] args) {
        runFlyway("db/h2/flyway.properties");
        runFlyway("db/postgres/flyway.properties");

        SpringApplication.run(SpringRestfulWebserviceApplication.class, args);
    }

    private void runFlyway(String resourcePath) {
        try {
            Utils.flywayMigrate(resourcePath);
        } catch (IOException e) {
            LOG.error(MessageFormat.format("Unable to obtain {0}", resourcePath), e);
        }
    }

}
