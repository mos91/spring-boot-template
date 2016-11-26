package org.mos91.restfulexample.repository.jdbc;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mos91.restfulexample.model.GreetingTemplate;
import org.mos91.restfulexample.repository.config.JdbcTemplatesRepositoryTestConfig;
import org.mos91.restfulexample.repository.config.TemplatesRepositoryTestQueries;
import org.mos91.restfulexample.repository.templates.TemplatesRepository;
import org.mos91.restfulexample.repository.templates.jdbc.GreetingTemplateMapper;
import org.mos91.restfulexample.utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.*;

/**
 * Created by Oleg.Meleshin on 10/24/2016.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {JdbcTemplatesRepositoryTestConfig.class})
@Transactional
public class JdbcTemplatesRepositoryTest {

    private final GreetingTemplateMapper templateMapper = new GreetingTemplateMapper();

    @Autowired
    TemplatesRepositoryTestQueries testQueries;

    @Autowired
    NamedParameterJdbcTemplate jdbcTemplate;

    @Autowired
    private TemplatesRepository templatesRepository;

    @Autowired
    private GreetingTemplate template1;

    @Autowired
    private GreetingTemplate template2;

    @BeforeClass
    public static void init() throws IOException {
        Utils.flywayMigrate("db/h2/flyway.properties");
    }

    @AfterClass
    public static void clear() throws IOException {
        Utils.flywayClean("db/h2/flyway.properties");
    }

    @Test
    public void testSaveTemplate() {
        templatesRepository.save(template1);

        List<GreetingTemplate> templates =
                jdbcTemplate.query(testQueries.selectAll(), templateMapper);
        Assert.assertEquals(1, templates.size());

        GreetingTemplate actualTemplate = templates.iterator().next();

        Assert.assertEquals(template1.getId(), actualTemplate.getId());
        Assert.assertEquals(template1.getContent(), actualTemplate.getContent());
    }

    @Test
    public void testSaveTemplates() {
        List<GreetingTemplate> templates = new ArrayList();

        templates.add(template1);
        templates.add(template2);
        templatesRepository.save(templates);

        templates = jdbcTemplate.query(testQueries.selectAll(), templateMapper);
        Assert.assertEquals(2, templates.size());
        Assert.assertTrue(templates.containsAll(Arrays.asList(template1, template2)));
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

        List<GreetingTemplate> templates = templatesRepository.findAll();
        Assert.assertTrue(templates.containsAll(Arrays.asList(template1, template2)));
    }

    @Test
    public void testFindAllByIds() {
        prepareTemplates(Arrays.asList(template1, template2));
        List<GreetingTemplate> templates = templatesRepository.findAll(
                Arrays.asList(template1.getId(), template2.getId()));

        Assert.assertTrue(templates.containsAll(Arrays.asList(template1, template2)));
    }

    @Test
    public void testCount() {
        prepareTemplates(Arrays.asList(template1, template2));
        long count = templatesRepository.count();

        Assert.assertEquals(2, count);
    }

    @Test
    public void testDelete() {
       prepareTemplates(Arrays.asList(template1, template2));

       templatesRepository.delete(template1.getId());

       List<GreetingTemplate> templates =
               jdbcTemplate.query(testQueries.selectAll(), templateMapper);
       Assert.assertEquals(1, templates.size());
       Assert.assertTrue(templates.contains(template2));
    }

    @Test
    public void testDeleteByIds() {
        prepareTemplates(Arrays.asList(template1, template2));

        templatesRepository.delete(Arrays.asList(template1, template2));

        List<GreetingTemplate> templates =
                jdbcTemplate.query(testQueries.selectAll(), templateMapper);
        Assert.assertEquals(0, templates.size());
    }

    @Test
    public void testDeleteAll() {
        prepareTemplates(Arrays.asList(template1, template2));

        List<GreetingTemplate> templates = jdbcTemplate.query(testQueries.selectAll(), templateMapper);
        Assert.assertEquals(2, templates.size());

        templatesRepository.deleteAll();

        templates = jdbcTemplate.query(testQueries.selectAll(), templateMapper);
        Assert.assertEquals(0, templates.size());
    }

    private void prepareTemplate(GreetingTemplate template) {
        Map<String, Object> params = new HashMap();
        params.put("id", template.getId());
        params.put("content", template.getContent());

        jdbcTemplate.update(testQueries.insert(), params);
    }

    private void prepareTemplates(List<GreetingTemplate> templates) {
        for (GreetingTemplate template : templates) {
            prepareTemplate(template);
        }
    }
}
