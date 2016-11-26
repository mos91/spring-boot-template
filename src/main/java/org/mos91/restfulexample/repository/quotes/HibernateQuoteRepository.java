package org.mos91.restfulexample.repository.quotes;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.mos91.restfulexample.model.Quote;
import org.mos91.restfulexample.repository.RepositoryType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HibernateQuoteRepository implements QuoteRepository {

  private static final Logger LOG = LoggerFactory.getLogger(HibernateQuoteRepository.class);

  private RepositoryType repositoryType;

  private SessionFactory sessionFactory;

  public HibernateQuoteRepository(RepositoryType repositoryType, SessionFactory sessionFactory) {
    this.repositoryType = repositoryType;
    this.sessionFactory = sessionFactory;
  }

  @Override
  public Quote save(Quote quote) {
    Session session = sessionFactory.getCurrentSession();

    LOG.debug("Storing {}", quote);

    session.save(quote);

    return quote;
  }
}
