package org.mos91.restfulexample.repository.templates.hb;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.mos91.restfulexample.model.GreetingTemplate;
import org.mos91.restfulexample.repository.RepositoryType;
import org.mos91.restfulexample.repository.templates.TemplatesRepository;
import org.mos91.restfulexample.repository.templates.jdbc.GreetingTemplateMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.List;

public class HibernateTemplatesRepository implements TemplatesRepository {

  private static final Logger LOG = LoggerFactory.getLogger(HibernateTemplatesRepository.class);

  private RepositoryType repositoryType;

  private SessionFactory sessionFactory;

  private TemplatesRepositoryQueries queries;

  public HibernateTemplatesRepository(RepositoryType repositoryType, SessionFactory sessionFactory, TemplatesRepositoryQueries queries) {
    this.repositoryType = repositoryType;
    this.sessionFactory = sessionFactory;
    this.queries = queries;
  }

  @Override
  @Transactional
  public <S extends GreetingTemplate> S save(S template) {
    Session session = sessionFactory.getCurrentSession();

    LOG.debug("Storing {}", template);

    session.save(template);

    return template;
  }

  @Override
  public <S extends GreetingTemplate> Collection<S> save(Collection<S> templates) {
    Session session = sessionFactory.getCurrentSession();

    templates.forEach(t -> session.save(t));

    return templates;
  }

  @Override
  public GreetingTemplate findOne(Long id) {
    Session session = sessionFactory.getCurrentSession();

    return session.get(GreetingTemplate.class, id);
  }

  @Override
  public boolean exists(Long id) {
    Session session = sessionFactory.getCurrentSession();

    return session.get(GreetingTemplate.class, id) != null;
  }

  @Override
  public List<GreetingTemplate> findAll() {
    Session session = sessionFactory.getCurrentSession();

    Criteria criteria = session.createCriteria(GreetingTemplate.class);

    return criteria.list();
  }

  @Override
  public List<GreetingTemplate> findAll(Collection<Long> ids) {
    Session session = sessionFactory.getCurrentSession();

    Criteria criteria = session.createCriteria(GreetingTemplate.class);
    criteria.add(Restrictions.in("id", ids));

    return criteria.list();
  }

  @Override
  public Long count() {
    Session session = sessionFactory.getCurrentSession();

    Criteria criteria = session.createCriteria(GreetingTemplate.class);
    criteria.setProjection(Projections.count("id"));

    return (Long) criteria.uniqueResult();
  }

  @Override
  public void delete(Long id) {
    Session session = sessionFactory.getCurrentSession();

    GreetingTemplate template = session.get(GreetingTemplate.class, id);
    session.delete(template);
  }

  @Override
  public void delete(GreetingTemplate template) {
    Session session = sessionFactory.getCurrentSession();

    session.delete(template);
  }

  @Override
  public void delete(Collection<? extends GreetingTemplate> templates) {
    Session session = sessionFactory.getCurrentSession();

    templates.stream().forEachOrdered(t -> session.delete(t));
  }

  @Override
  @Transactional
  public void deleteAll() {
    Session session = sessionFactory.getCurrentSession();

    List<GreetingTemplate> templates = session.createCriteria(GreetingTemplate.class).list();
    for (GreetingTemplate template : templates) {
        session.delete(template);
    }
  }
}
