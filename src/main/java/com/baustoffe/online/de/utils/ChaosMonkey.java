package com.baustoffe.online.de.utils;

import java.util.Date;
import org.apache.commons.lang3.RandomUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import com.baustoffe.online.de.ui.event.MonkeyEvent;
import com.baustoffe.online.de.ui.event.StatusEvent;
import com.google.common.eventbus.EventBus;
import com.vaadin.spring.annotation.SpringComponent;

/**
 * @author Benjamin Wilms (xd98870)
 */
@SpringComponent
public class ChaosMonkey {

    private static final Logger log = LoggerFactory.getLogger(ChaosMonkey.class);

    @Autowired
    private EventBus eventBus;

    private boolean trouble = false;

    private boolean enableException = false;

    public void callForTrouble(String service) {

        if (trouble) {
            int i = RandomUtils.nextInt(1, 10);

            log.info("### >" + i);

            if (i <= 2) {
                // Timeout Monkey
                try {
                    eventBus.post(new MonkeyEvent("Chaos Monkey - Timeout von 3s >>> " + service, new Date()));
                    eventBus.post(new StatusEvent("error", service.toLowerCase().substring(0, 1))); // Label der System refresh

                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    log.debug("Thread sleep abgebrochen...");
                }
            } else if (i > 2 && i <= 3) {
                if (enableException) {
                    eventBus.post(new MonkeyEvent("Chaos Monkey - ERROR", new Date()));
                    throw new RuntimeException("Monkey Runtime Exception");
                }
            }
        }

    }

    public void activate() {
        log.info("Chaos Monkey ist aktiv!!!");
        trouble = true;
    }

    public void disable() {
        log.info("Chaos Monkey ist inaktiv!!!");
        trouble = false;
    }

    public void throwExceptions(boolean value) {
        enableException = value;
    }
}
