package org.mos91.restfulexample.repository.templates.jdbc;

import org.mos91.restfulexample.model.GreetingTemplate;
import org.mos91.restfulexample.repository.RepositoryType;
import org.mos91.restfulexample.repository.templates.TemplatesRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.transaction.support.TransactionTemplate;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.mos91.restfulexample.repository.templates.jdbc.GreetingTemplateMapper.TABLE_NAME;

/**
 * Created by Oleg.Meleshin on 10/24/2016.
 */
public class JdbcTemplatesRepository implements TemplatesRepository {

    private static final Logger LOG = LoggerFactory.getLogger(JdbcTemplatesRepository.class);

    private final GreetingTemplateMapper templateMapper = new GreetingTemplateMapper();

    private final RepositoryType repositoryType;

    private final DataSource dataSource;

    private final NamedParameterJdbcTemplate jdbcTemplate;

    private TransactionTemplate transactionTemplate;

    private TemplatesRepositoryQueries queries;

    public JdbcTemplatesRepository(RepositoryType repositoryType,
        DataSource dataSource, NamedParameterJdbcTemplate jdbcTemplate,
        TransactionTemplate transactionTemplate, TemplatesRepositoryQueries queries) {
        this.repositoryType = repositoryType;
        this.dataSource = dataSource;
        this.jdbcTemplate = jdbcTemplate;
        this.transactionTemplate = transactionTemplate;
        this.queries = queries;
    }

    @Override
    public GreetingTemplate save(GreetingTemplate template) {
        LOG.debug("Storing {} to {}", template, repositoryType);

        return transactionTemplate.execute(status -> insertTemplate(template));
    }

    @Override
    public <S extends GreetingTemplate> Collection<S> save(Collection<S> templates) {
        LOG.debug("Storing batch to {}", templates, repositoryType);

        return transactionTemplate.execute(status -> {
            for (GreetingTemplate template : templates) {
                insertTemplate(template);
            }

            return templates;
        });
    }

    @Override
    public GreetingTemplate findOne(Long id) {
        LOG.debug("Extract by id = {}", id);

        Map<String, Object> params = new HashMap();
        params.put("id", id);

        return jdbcTemplate.queryForObject(
                queries.selectById(), params, templateMapper);
    }

    @Override
    public boolean exists(Long id) {
        LOG.debug("Extract by id = {}", id);
        Map<String, Object> params = new HashMap();
        params.put("id", id);

        Long count = jdbcTemplate.queryForObject(queries.count(), params, Long.class);

        return count > 0;
    }

    @Override
    public List<GreetingTemplate> findAll() {
        LOG.debug("Extract all");

        return jdbcTemplate.query(queries.selectAll(), templateMapper);
    }

    @Override
    public List<GreetingTemplate> findAll(Collection<Long> ids) {
        LOG.debug("Extract all by ids ({})", ids);

        return jdbcTemplate.query(queries.selectAllByIds(ids), templateMapper);
    }

    @Override
    public Long count() {
        LOG.debug("Count all templates");

        return jdbcTemplate.queryForObject(queries.count(), new HashMap(), Long.class);
    }

    @Override
    public void delete(Long id) {
        LOG.debug("Delete template by id = {}", id);

        transactionTemplate.execute(status -> deleteTemplate(id));
        deleteTemplate(id);
    }

    @Override
    public void delete(GreetingTemplate template) {
        LOG.debug("Delete template by id = {}", template.getId());

        transactionTemplate.execute(status -> deleteTemplate(template.getId()));
    }

    @Override
    public void delete(Collection<? extends GreetingTemplate> templates) {
        List<Long> ids = new ArrayList();
        templates.forEach(t -> ids.add(t.getId()));

        LOG.debug("Delete by ids = {}", ids);

        transactionTemplate.execute(status -> jdbcTemplate.update(queries.deleteByIds(ids), new HashMap()));
    }

    @Override
    public void deleteAll() {
        LOG.debug("Delete all");

        transactionTemplate.execute(status -> jdbcTemplate.update(queries.deleteAll(), new HashMap()));
    }

    protected GreetingTemplate insertTemplate(GreetingTemplate template) {
        Map<String, Object> params = new HashMap();
        params.put("content", template.getContent());

        Long newId = (Long) new SimpleJdbcInsert(dataSource).withTableName(TABLE_NAME)
                .usingGeneratedKeyColumns("id").executeAndReturnKey(params);
        template.setId(newId);

        return template;
    }

    protected int deleteTemplate(Long id) {
        Map<String, Object> params = new HashMap();
        params.put("id", id);

        return jdbcTemplate.update(queries.deleteById(), params);
    }
}
