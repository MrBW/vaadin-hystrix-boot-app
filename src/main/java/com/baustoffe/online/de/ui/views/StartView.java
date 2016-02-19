package com.baustoffe.online.de.ui.views;

import java.util.Date;
import com.baustoffe.online.de.dto.ArtikelDTO;
import com.baustoffe.online.de.services.BaushopService;
import com.baustoffe.online.de.ui.elements.ArtikelGrid;
import com.baustoffe.online.de.ui.event.MonkeyEvent;
import com.baustoffe.online.de.utils.ChaosMonkey;
import com.google.common.eventbus.EventBus;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.VerticalLayout;

/**
 * @author Benjamin Wilms (xd98870)
 */
@SpringView
public class StartView extends VerticalLayout implements View {

    private BaushopService baushopService;

    private BeanItemContainer<ArtikelDTO> bic = new BeanItemContainer<>(ArtikelDTO.class);

    private ChaosMonkey chaosMonkey;

    private EventBus eventBus;

    private ArtikelGrid artikelGrid = new ArtikelGrid();

    public StartView(BaushopService service, ChaosMonkey chaosMonkey, EventBus eventBus) {
        this.baushopService = service;
        this.chaosMonkey = chaosMonkey;
        this.eventBus = eventBus;

        artikelGrid.getButtonRefresh().addClickListener(event -> reloadBIC());

        addComponent(artikelGrid);
        setComponentAlignment(artikelGrid, Alignment.MIDDLE_CENTER);

        setSizeFull();

    }

    private void reloadBIC() {
        artikelGrid.getBic().removeAllItems();
        artikelGrid.getBic().addAll(baushopService.getAlleArtikel());
        eventBus.post(new MonkeyEvent("Daten wurden geladen", new Date()));
    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent viewChangeEvent) {

        chaosMonkey.disable();
        eventBus.post("ok");

    }

}
