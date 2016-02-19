package com.baustoffe.online.de.scheduler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import com.baustoffe.online.de.services.BaushopService;

/**
 * @author Benjamin Wilms (xd98870)
 */
public class ServiceCallScheduler {

    @Autowired
    private BaushopService service;

    @Scheduled(fixedRate = 3)
    public void callingServices() {

    }
}
