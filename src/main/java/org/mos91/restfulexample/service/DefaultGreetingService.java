package org.mos91.restfulexample.service;

import org.mos91.restfulexample.model.Greeting;
import org.mos91.restfulexample.model.GreetingTemplate;
import org.mos91.restfulexample.repository.templates.TemplatesRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;
import java.util.Collection;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Created by Oleg.Meleshin on 10/24/2016.
 */
@Service
public class DefaultGreetingService implements GreetingService {

    private static final Logger LOG = LoggerFactory.getLogger(DefaultGreetingService.class);

    private static final String DEFAULT_TEMPLATE = "Hello, {0}!";

    private AtomicLong counter = new AtomicLong();

    @Autowired
    @Qualifier("pgTemplatesRepository")
    private TemplatesRepository templatesRepository;

    @Override
    public Greeting greeting(String name) {
        Greeting greeting = buildGreeting(name, null);

        LOG.info("User greetings, {}", greeting);

        return greeting;
    }

    @Override
    public Greeting greeting(String name, long templateId) {
        return buildGreeting(name, templateId);
    }

    @Override
    public GreetingTemplate saveTemplate(GreetingTemplate template) {
        return templatesRepository.save(template);
    }

    @Override
    public Collection<GreetingTemplate> findAllTemplate() {
        return templatesRepository.findAll();
    }

    @Override
    public GreetingTemplate findTemplateById(long id) {
        return templatesRepository.findOne(id);
    }

    private Greeting buildGreeting(String name, Long id) {
        Greeting greeting;

        if (id == null) {
            greeting = new Greeting(counter.incrementAndGet(), MessageFormat.format(DEFAULT_TEMPLATE, name));
        } else {
            GreetingTemplate template = templatesRepository.findOne(id);

            if (template == null) {
                throw new IllegalStateException("Template with specified id does not exist");
            }

            greeting = new Greeting(counter.incrementAndGet(), template.getContent());
        }

        return greeting;
    }

}
