package com.baustoffe.online.de.scheduler;

import org.apache.commons.lang3.RandomUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.baustoffe.online.de.hystrix.BauhuetteHystrix;
import com.baustoffe.online.de.hystrix.PraktischHystrix;
import com.baustoffe.online.de.hystrix.UbiHystrix;
import com.baustoffe.online.de.services.BauhuetteRemoteService;
import com.baustoffe.online.de.services.PraktischRemoteService;
import com.baustoffe.online.de.services.UbiRemoteService;
import com.baustoffe.online.de.utils.ChaosMonkey;

/**
 * @author Benjamin Wilms (xd98870)
 */
@Component
public class ServiceCallScheduler {
    private static final Logger log = LoggerFactory.getLogger(ServiceCallScheduler.class);

    @Autowired
    private PraktischRemoteService praktischRemoteService;

    @Autowired
    private UbiRemoteService ubiRemoteService;

    @Autowired
    private ChaosMonkey chaosMonkey;

    @Autowired
    private BauhuetteRemoteService bauhuetteRemoteService;


    @Scheduled(fixedRate = 3000)
    public void callingServices() {

        log.debug("Call Service");
        int i = RandomUtils.nextInt(1, 20);

        if (i <= 1)
            chaosMonkey.activate();
        else
            chaosMonkey.disable();

        if (i == 1) {
            new BauhuetteHystrix(bauhuetteRemoteService, null).queue();
        } else if (i == 2) {
            new PraktischHystrix(praktischRemoteService, null).queue();
        } else if (i == 3) {
            new UbiHystrix(ubiRemoteService, null).queue();
        }
    }
}
