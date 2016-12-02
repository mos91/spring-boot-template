package org.mos91.restfulexample.scheduling;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Oleg.Meleshin on 10/18/2016.
 */
@Component
public class CurrentTime {

    private static final Logger LOG = LoggerFactory.getLogger(CurrentTime.class);

    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("HH:mm:ss:z");

    @Scheduled(fixedRate = 5000)
    public void reportCurrentTime() {
        LOG.info("The time is now {}", DATE_FORMAT.format(new Date()));
    }
}
