package org.mos91.restfulexample.scheduling;

import org.mos91.restfulexample.model.Quote;
import org.mos91.restfulexample.repository.quotes.QuoteRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.support.TransactionTemplate;
import org.springframework.web.client.RestTemplate;

import static org.mos91.restfulexample.commons.transactions.Transactions.executedBy;

/**
 * Created by Oleg.Meleshin on 10/18/2016.
 */
@Component
public class QuotePoller {

    @Autowired
    @Qualifier("pgQuoteRepository")
    private QuoteRepository pgQuoteRepository;

    @Autowired
    @Qualifier("h2QuoteRepository")
    private QuoteRepository h2QuoteRepository;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    @Qualifier("pgTransactionTemplate")
    private TransactionTemplate pgTransactionTemplate;

    @Autowired
    @Qualifier("h2TransactionTemplate")
    private TransactionTemplate h2TransactionTemplate;

    @Value("${remote.url}")
    private String remoteUrl;

    private static final Logger LOG = LoggerFactory.getLogger(QuotePoller.class);

    @Scheduled(fixedRate = 10000)
    public void pollQuote() {
        LOG.info("Get from {}", remoteUrl);

        Quote quote = restTemplate.getForObject(remoteUrl, Quote.class);

        saveToH2(quote);
        saveToPg(quote);
    }

    private void saveToH2(final Quote quote) {
        Quote quoteToSave = quote.copyOf();

        executedBy(h2TransactionTemplate).body(status -> h2QuoteRepository.save(quote)).execute();

        LOG.info("Save to DB(H2) : {}", quoteToSave);
    }

    private void saveToPg(final Quote quote) {
        Quote quoteToSave = quote.copyOf();

        executedBy(pgTransactionTemplate).body(status -> pgQuoteRepository.save(quote)).execute();

        LOG.info("Save to DB(Postgres) : {}", quoteToSave);
    }
}
