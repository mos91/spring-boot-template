package org.mos91.restfulexample.repository.hb;

import org.hamcrest.CoreMatchers;
import org.hibernate.SessionFactory;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mos91.restfulexample.model.GreetingTemplate;
import org.mos91.restfulexample.repository.config.HbTemplatesRepositoryTestConfig;
import org.mos91.restfulexample.repository.config.TemplatesRepositoryTestQueries;
import org.mos91.restfulexample.repository.templates.TemplatesRepository;
import org.mos91.restfulexample.repository.templates.jdbc.GreetingTemplateMapper;
import org.mos91.restfulexample.utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.*;

import static org.hamcrest.CoreMatchers.instanceOf;

/**
 * Created by Oleg.Meleshin on 11/18/2016.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {HbTemplatesRepositoryTestConfig.class})
@Transactional
public class HbTemplatesRepositoryTest {

    @Autowired
    private SessionFactory sessionFactory;

    @Autowired
    private TemplatesRepository templatesRepository;

    @Autowired
    private GreetingTemplate template1;

    @Autowired
    private GreetingTemplate template2;

    @Before
    public void init() throws IOException {
        Utils.flywayMigrate("db/h2/flyway.properties");
    }

    @After
    public void clear() throws IOException {
        Utils.flywayClean("db/h2/flyway.properties");
    }

    @Test
    public void testSaveTemplate() {
        templatesRepository.save(template1);

        GreetingTemplate actualTemplate =
                sessionFactory.getCurrentSession().get(GreetingTemplate.class, template1.getId());
        Assert.assertEquals(template1.getId(), actualTemplate.getId());
        Assert.assertEquals(template1.getContent(), actualTemplate.getContent());
    }

    @Test
    public void testSaveTemplates() {
        List<GreetingTemplate> templates = new ArrayList();

        templates.add(template1);
        templates.add(template2);
        templatesRepository.save(templates);

        List<GreetingTemplate> actualTemplates = new ArrayList();
        actualTemplates.add(sessionFactory.getCurrentSession().get(GreetingTemplate.class, template1.getId()));
        actualTemplates.add(sessionFactory.getCurrentSession().get(GreetingTemplate.class, template2.getId()));

        Assert.assertEquals(2, actualTemplates.size());
        Assert.assertTrue(actualTemplates.containsAll(Arrays.asList(template1, template2)));
    }

    @Test
    public void testFindOne() {
        prepareTemplate(template1);

        GreetingTemplate template = templatesRepository.findOne(template1.getId());
        Assert.assertEquals(template1.getId(), template.getId());
        Assert.assertEquals(template1.getContent(), template.getContent());
    }

    @Test
    public void testExists() {
        prepareTemplate(template1);
        Assert.assertTrue(templatesRepository.exists(template1.getId()));
    }

    @Test
    public void testFindAll() {
        prepareTemplates(Arrays.asList(template1, template2));

        sessionFactory.getCurrentSession().flush();

        List<GreetingTemplate> templates = templatesRepository.findAll();
        Assert.assertTrue(templates.containsAll(Arrays.asList(template1, template2)));
    }

    @Test
    public void testFindAllByIds() {
        prepareTemplates(Arrays.asList(template1, template2));

        sessionFactory.getCurrentSession().flush();

        List<GreetingTemplate> templates = templatesRepository.findAll(
                Arrays.asList(template1.getId(), template2.getId()));

        Assert.assertTrue(templates.containsAll(Arrays.asList(template1, template2)));
    }

    @Test
    public void testCount() {
        prepareTemplates(Arrays.asList(template1, template2));

        sessionFactory.getCurrentSession().flush();

        long count = templatesRepository.count();

        Assert.assertEquals(2, count);
    }

    @Test
    public void testDelete() {
        prepareTemplates(Arrays.asList(template1, template2));

        assertTemplatesExist();

        templatesRepository.delete(template1.getId());

        Assert.assertNull(sessionFactory.getCurrentSession().get(GreetingTemplate.class, template1.getId()));
        GreetingTemplate template = sessionFactory.getCurrentSession().get(GreetingTemplate.class, template2.getId());
        Assert.assertNotNull(template);
        Assert.assertEquals(template2.getId(), template.getId());
    }

    @Test
    public void testDeleteByIds() {
        prepareTemplates(Arrays.asList(template1, template2));

        assertTemplatesExist();

        templatesRepository.delete(Arrays.asList(template1, template2));

        Assert.assertNull(sessionFactory.getCurrentSession().get(GreetingTemplate.class, template1.getId()));
        Assert.assertNull(sessionFactory.getCurrentSession().get(GreetingTemplate.class, template2.getId()));
    }

    @Test
    public void testDeleteAll() {
        prepareTemplates(Arrays.asList(template1, template2));

        sessionFactory.getCurrentSession().flush();

        assertTemplatesExist();

        templatesRepository.deleteAll();

        Assert.assertNull(sessionFactory.getCurrentSession().get(GreetingTemplate.class, template1.getId()));
        Assert.assertNull(sessionFactory.getCurrentSession().get(GreetingTemplate.class, template2.getId()));
    }

    private void prepareTemplate(GreetingTemplate template) {
        sessionFactory.getCurrentSession().save(template);
    }

    private void prepareTemplates(List<GreetingTemplate> templates) {
        for (GreetingTemplate template : templates) {
            prepareTemplate(template);
        }
    }

    private void assertTemplatesExist() {
        GreetingTemplate template = sessionFactory.getCurrentSession().get(GreetingTemplate.class, template1.getId());
        Assert.assertThat(template, instanceOf(GreetingTemplate.class));
        Assert.assertEquals(template1.getId(), template.getId());
        template = sessionFactory.getCurrentSession().get(GreetingTemplate.class, template2.getId());
        Assert.assertThat(template, instanceOf(GreetingTemplate.class));
        Assert.assertEquals(template2.getId(), template.getId());
    }
}
