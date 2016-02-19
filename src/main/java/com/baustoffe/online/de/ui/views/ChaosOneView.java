package com.baustoffe.online.de.ui.views;

import java.util.Date;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.baustoffe.online.de.services.BaushopService;
import com.baustoffe.online.de.ui.elements.ArtikelGrid;
import com.baustoffe.online.de.ui.event.MonkeyEvent;
import com.baustoffe.online.de.utils.ChaosMonkey;
import com.google.common.eventbus.EventBus;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.VerticalLayout;

/**
 * Standard Aufruf der Services mit aktiven Chaos Monkey
 * @author Benjamin Wilms (xd98870)
 */
@SpringView
public class ChaosOneView extends VerticalLayout implements View {

    private static final Logger log = LoggerFactory.getLogger(ChaosOneView.class);

    private final EventBus eventBus;

    private BaushopService baushopService;

    private ChaosMonkey chaosMonkey;

    private ArtikelGrid artikelGrid = new ArtikelGrid();

    public ChaosOneView(BaushopService service, ChaosMonkey chaosMonkey, EventBus eventBus) {
        this.baushopService = service;
        this.chaosMonkey = chaosMonkey;
        this.eventBus = eventBus;

        artikelGrid.getButtonRefresh().addClickListener(event -> refreshDataGrid());

        artikelGrid.setSizeFull();

        addComponent(artikelGrid);
        setSizeFull();
    }

    private void refreshDataGrid() {

        try {
            artikelGrid.getBic().removeAllItems();
            artikelGrid.getBic().addAll(baushopService.getAlleArtikel());
            eventBus.post(new MonkeyEvent("Daten wurden geladen", new Date()));

        } catch (Exception e) {
            log.error("Fehler beim Lesen der Daten", e);
        }
    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent viewChangeEvent) {

        eventBus.post("ok");
        eventBus.post(true);
        chaosMonkey.activate();
        artikelGrid.getBic().removeAllItems();

    }

}
