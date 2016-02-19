package com.baustoffe.online.de.ui;

import java.text.SimpleDateFormat;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import com.baustoffe.online.de.services.BauhuetteRemoteService;
import com.baustoffe.online.de.services.BaushopService;
import com.baustoffe.online.de.services.PraktischRemoteService;
import com.baustoffe.online.de.services.UbiRemoteService;
import com.baustoffe.online.de.ui.event.MonkeyEvent;
import com.baustoffe.online.de.ui.event.StatusEvent;
import com.baustoffe.online.de.ui.views.*;
import com.baustoffe.online.de.utils.ChaosMonkey;
import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;
import com.vaadin.annotations.Push;
import com.vaadin.annotations.Theme;
import com.vaadin.navigator.Navigator;
import com.vaadin.server.VaadinRequest;
import com.vaadin.shared.communication.PushMode;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.ui.Label;
import com.vaadin.ui.TextArea;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;

/**
 * @author Benjamin Wilms (xd98870)
 */
@Theme("valo")
@SpringUI(path = "")
@Push(PushMode.AUTOMATIC)
public class BaushopUI extends UI {

    protected static final String CHAOS_ONE = "chaos1";

    protected static final String HYSTRIX_1 = "hx1";

    protected static final String HYSTRIX_2 = "hx2";

    protected static final String HYSTRIX_DASH = "hxboard";

    private static final Logger log = LoggerFactory.getLogger(BaushopUI.class);

    Label labelBauhuette, labelPraktisch, labelUbi;

    @Autowired
    private BaushopService baushopService;

    @Autowired
    private BauhuetteRemoteService bauhuetteRemoteService;

    @Autowired
    private PraktischRemoteService praktischRemoteService;

    @Autowired
    private UbiRemoteService ubiRemoteService;

    @Autowired
    private EventBus eventBus;

    @Autowired
    private ChaosMonkey chaosMonkey;

    private TextArea logArea = new TextArea();

    private Navigator navigator;

    @Override
    protected void init(VaadinRequest request) {

        // Events fuer die Logausgabe
        eventBus.register(this);
        eventBus.post("Event Bus gestartet...");

        chaosMonkey.disable();

        setSizeFull();

        VerticalLayout verticalLayout = new VerticalLayout();
        verticalLayout.setMargin(true);
        VerticalLayout tableLayout = new VerticalLayout();
        tableLayout.setSizeFull();
        VerticalLayout logLayout = new VerticalLayout();

        verticalLayout.addComponent(tableLayout);
        verticalLayout.addComponent(logLayout);
        verticalLayout.setExpandRatio(tableLayout, 5);
        verticalLayout.setExpandRatio(logLayout, 2);
        verticalLayout.setSizeFull();

        Label label = new Label("<b>Logs:</b>");
        label.setContentMode(ContentMode.HTML);
        label.setSizeFull();

        // labelBauhuette = new Label("Bauhuette");
        // labelBauhuette.setContentMode(ContentMode.HTML);
        // labelPraktisch = new Label("Praktisch");
        // labelPraktisch.setContentMode(ContentMode.HTML);
        // labelUbi = new Label("Ubi");
        // labelUbi.setContentMode(ContentMode.HTML);

        // HorizontalLayout hLaySysteme = new HorizontalLayout();
        // hLaySysteme.addComponent(label);
        // hLaySysteme.setExpandRatio(label, 3);
        // hLaySysteme.addComponent(labelBauhuette);
        // hLaySysteme.setExpandRatio(labelBauhuette, 2);
        // hLaySysteme.addComponent(labelPraktisch);
        // hLaySysteme.setExpandRatio(labelPraktisch, 2);
        // hLaySysteme.addComponent(labelUbi);
        // hLaySysteme.setExpandRatio(labelUbi, 2);
        // hLaySysteme.setMargin(new MarginInfo(false, true, false, false));
        // hLaySysteme.setSizeFull();

        logArea.setReadOnly(true);
        logArea.setNullRepresentation("");
        logArea.setImmediate(true);
        logArea.setSizeFull();

        logLayout.addComponent(label);
        logLayout.addComponent(logArea);
        logLayout.setExpandRatio(label, 2);
        logLayout.setExpandRatio(logArea, 10);
        logLayout.setSizeFull();
        logLayout.setMargin(false);

        setContent(verticalLayout);

        // Create a navigator to control the views
        navigator = new Navigator(this, tableLayout);

        initNavigator();

        // Navigation anhand der URI
        String fragment = getPage().getUriFragment();
        if (StringUtils.isNotEmpty(fragment)) {
            navigator.navigateTo(fragment);

            if (fragment.equals(HYSTRIX_DASH)) {
                logLayout.setVisible(false);

            }

        }

    }

    private void initNavigator() {
        // Create and register the views
        navigator.addView("", new StartView(baushopService, chaosMonkey, eventBus));
        navigator.addView(CHAOS_ONE, new ChaosOneView(baushopService, chaosMonkey, eventBus));
        navigator.addView(HYSTRIX_1, new HystrixSyncView(baushopService, chaosMonkey, eventBus));
        navigator.addView(HYSTRIX_2, new HystrixQueueView(baushopService, chaosMonkey, eventBus));
        navigator.addView(HYSTRIX_DASH,
            new HystrixDashboardView(bauhuetteRemoteService, praktischRemoteService, ubiRemoteService, chaosMonkey));
    }

    @Subscribe
    public void event(MonkeyEvent event) {

        getUI().access(() -> {
            try {
                SimpleDateFormat df = new SimpleDateFormat("hh:mm:ss");

                String oldValues = logArea.getValue();
                logArea.setReadOnly(false);
                logArea.setValue(df.format(event.getTime()) + ": " + event.getMessage() + "\n" + oldValues);
                logArea.setReadOnly(true);
            } catch (Exception e) {
                log.error("Event konnte nicht verarbeitet werden", e);
            }
        });

    }

    @Subscribe
    public void changeSystemStatus(StatusEvent event) {
        // try {
        // if (event.getSystem().equals("b")) {
        // labelBauhuette.setValue("<b>Bauhuette > " + event.getStatus() + "</b>");
        // labelBauhuette.markAsDirty();
        // } else if (event.getSystem().equals("p")) {
        // labelPraktisch.setValue("<b>Praktisch > " + event.getStatus() + "</b>");
        // labelPraktisch.markAsDirty();
        // } else if (event.getSystem().equals("u")) {
        // labelUbi.setValue("<b>Ubi > " + event.getStatus() + "</b>");
        // labelUbi.markAsDirty();
        //
        // }
        //
        // } catch (Exception e) {
        // log.error("Event konnte nicht verarbeitet werden", e);
        // }
    }

    @Subscribe
    public void changeSystemStatusDefault(String status) {
        // try {
        // if (labelBauhuette != null) {
        // labelBauhuette.setValue("<b>Bauhuette</b> > " + status);
        //
        // labelBauhuette.markAsDirty();
        // }
        //
        // if (labelPraktisch != null) {
        // labelPraktisch.setValue("<b>Praktisch</b> > " + status);
        //
        // labelPraktisch.markAsDirty();
        // }
        //
        // if (labelUbi != null) {
        // labelUbi.setValue("<b>Ubi</b> > " + status);
        // labelUbi.markAsDirty();
        // }
        //
        // logArea.setReadOnly(false);
        // logArea.setValue("");
        // logArea.setReadOnly(true);
        //
        // } catch (Exception e) {
        // log.error("Event konnte nicht verarbeitet werden", e);
        // }
    }

    @Subscribe
    public void cleanLog(boolean clean) {
        logArea.setReadOnly(false);
        logArea.setValue("");

        logArea.setReadOnly(true);
    }

}