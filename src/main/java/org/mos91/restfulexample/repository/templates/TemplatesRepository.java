package org.mos91.restfulexample.repository.templates;

import org.mos91.restfulexample.model.GreetingTemplate;

import java.util.Collection;
import java.util.List;

/**
 * Created by Oleg.Meleshin on 10/24/2016.
 */
public interface TemplatesRepository {

    <S extends GreetingTemplate> S save(S entity);

    <S extends GreetingTemplate> Collection<S> save(Collection<S> entities);

    GreetingTemplate findOne(Long id);

    boolean exists(Long id);

    List<GreetingTemplate> findAll();

    List<GreetingTemplate> findAll(Collection<Long> ids);

    Long count();

    void delete(Long id);

    void delete(GreetingTemplate entity);

    void delete(Collection<? extends GreetingTemplate> entities);

    void deleteAll();
}
