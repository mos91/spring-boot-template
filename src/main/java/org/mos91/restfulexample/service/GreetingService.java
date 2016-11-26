package org.mos91.restfulexample.service;

import org.mos91.restfulexample.model.Greeting;
import org.mos91.restfulexample.model.GreetingTemplate;

import java.util.Collection;

/**
 * Created by Oleg.Meleshin on 10/24/2016.
 */
public interface GreetingService {

    Greeting greeting(String name);

    Greeting greeting(String name, long templateId);

    GreetingTemplate saveTemplate(GreetingTemplate template);

    Collection<GreetingTemplate> findAllTemplate();

    GreetingTemplate findTemplateById(long id);

}
