package org.mos91.restfulexample.utils;

import org.flywaydb.core.Flyway;
import org.hibernate.SessionFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;

import javax.sql.DataSource;
import java.io.IOException;
import java.util.Properties;

/**
 * Created by Oleg.Meleshin on 10/20/2016.
 */
public final class Utils {

    private Utils() {

    }

    private static Flyway flyway(String propertiesPath) throws IOException {
        Flyway flyway = new Flyway();
        Properties properties = new Properties();
        properties.load(new ClassPathResource(propertiesPath).getInputStream());

        flyway.configure(properties);

        return flyway;
    }

    public static void flywayMigrate(String propertiesPath) throws IOException {
        flyway(propertiesPath).migrate();
    }

    public static void flywayClean(String propertiesPath) throws IOException {
        flyway(propertiesPath).clean();
    }

    public static SessionFactory createSessionFactory(DataSource dataSource) throws IOException {
        LocalSessionFactoryBean factoryBean = new LocalSessionFactoryBean();
        factoryBean.setDataSource(dataSource);
        factoryBean.setPackagesToScan("org.mos91.restfulexample.model");
        factoryBean.afterPropertiesSet();

        return factoryBean.getObject();
    }

}
