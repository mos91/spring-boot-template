package org.mos91.restfulexample.repository.quotes;

import org.mos91.restfulexample.model.Quote;
import org.mos91.restfulexample.repository.RepositoryType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Oleg.Meleshin on 10/20/2016.
 */
public class JdbcQuoteRepository implements QuoteRepository {

    private static final Logger LOG = LoggerFactory.getLogger(JdbcQuoteRepository.class);

    private RepositoryType repositoryType;

    private NamedParameterJdbcTemplate jdbcTemplate;

    public JdbcQuoteRepository(RepositoryType repositoryType, NamedParameterJdbcTemplate jdbcTemplate) {
        this.repositoryType = repositoryType;
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Quote save(Quote quote) {
        LOG.info("Storing {} to {}", quote, repositoryType);

        Map<String, Object> params = new HashMap();
        params.put("type", quote.getType());
        params.put("quote", quote.getValue().getQuote());

        jdbcTemplate.update("INSERT INTO QUOTE_TBL(type, quote) VALUES(:type, :quote)", params);

        return quote;
    }
}
