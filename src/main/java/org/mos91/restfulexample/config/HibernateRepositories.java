package org.mos91.restfulexample.config;

import org.hibernate.SessionFactory;
import org.mos91.restfulexample.repository.RepositoryType;
import org.mos91.restfulexample.repository.quotes.HibernateQuoteRepository;
import org.mos91.restfulexample.repository.quotes.QuoteRepository;
import org.mos91.restfulexample.repository.templates.TemplatesRepository;
import org.mos91.restfulexample.repository.templates.hb.HibernateTemplatesRepository;
import org.mos91.restfulexample.repository.templates.hb.TemplatesRepositoryQueries;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import java.io.IOException;

@Configuration
@Profile("hibernate-repositories")
public class HibernateRepositories {

  @Bean
  @Qualifier("h2QuoteRepository")
  public QuoteRepository h2QuoteRepository(@Qualifier("h2SessionFactory") SessionFactory sessionFactory) {
    return new HibernateQuoteRepository(RepositoryType.H2, sessionFactory);
  }

  @Bean
  @Qualifier("pgQuoteRepository")
  public QuoteRepository pgQuoteRepository(@Qualifier("pgSessionFactory") SessionFactory sessionFactory) {
    return new HibernateQuoteRepository(RepositoryType.POSTGRES, sessionFactory);
  }

  @Bean
  @Qualifier("templateQueries")
  public TemplatesRepositoryQueries queries() throws IOException {
    return new TemplatesRepositoryQueries("db/hql/queries/TemplateRepository.yaml");
  }

  @Bean
  @Qualifier("h2TemplatesRepository")
  public TemplatesRepository h2TemplatesRepository(@Qualifier("h2SessionFactory") SessionFactory sessionFactory,
    @Qualifier("templateQueries") TemplatesRepositoryQueries queries) {
    return new HibernateTemplatesRepository(RepositoryType.H2, sessionFactory, queries);
  }

  @Bean
  @Qualifier("pgTemplatesRepository")
  public TemplatesRepository pgTemplatesRepository(@Qualifier("pgSessionFactory") SessionFactory sessionFactory,
    @Qualifier("templateQueries") TemplatesRepositoryQueries queries) {
    return new HibernateTemplatesRepository(RepositoryType.POSTGRES, sessionFactory, queries);
  }
}
