package com.baustoffe.online.de.ui.views;

import org.apache.commons.lang3.RandomUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.baustoffe.online.de.hystrix.BauhuetteHystrix;
import com.baustoffe.online.de.hystrix.PraktischHystrix;
import com.baustoffe.online.de.hystrix.UbiHystrix;
import com.baustoffe.online.de.services.BauhuetteRemoteService;
import com.baustoffe.online.de.services.PraktischRemoteService;
import com.baustoffe.online.de.services.UbiRemoteService;
import com.baustoffe.online.de.utils.ChaosMonkey;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.Button;
import com.vaadin.ui.HorizontalLayout;

/**
 * @author Benjamin Wilms (xd98870)
 */
@SpringView
public class HystrixDashboardView extends HorizontalLayout implements View {

    private static final Logger log = LoggerFactory.getLogger(HystrixDashboardView.class);

    private final PraktischRemoteService praktischRemoteService;

    private final UbiRemoteService ubiRemoteService;

    private final ChaosMonkey chaosMonkey;

    private BauhuetteRemoteService bauhuetteRemoteService;

    public HystrixDashboardView(BauhuetteRemoteService bauhuetteRemoteService, PraktischRemoteService praktischRemoteService,
            UbiRemoteService ubiRemoteService, ChaosMonkey chaosMonkey) {
        this.bauhuetteRemoteService = bauhuetteRemoteService;
        this.praktischRemoteService = praktischRemoteService;
        this.ubiRemoteService = ubiRemoteService;
        this.chaosMonkey = chaosMonkey;

        Button start = new Button("Starte Anfragen...");

        start.addClickListener(event -> starteThreads());

        setImmediate(true);

        addComponent(start);

    }

    private void starteThreads() {

        int counter = 0;
        while (counter < 3000) {
            log.debug("Call Service");
            int i = RandomUtils.nextInt(1, 20);
            log.debug("Int: " + i);

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

            counter++;

        }

    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event) {
        chaosMonkey.activate();
        chaosMonkey.throwExceptions(true);

    }
}
