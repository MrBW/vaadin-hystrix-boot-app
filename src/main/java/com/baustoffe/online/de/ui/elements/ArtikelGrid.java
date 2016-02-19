package com.baustoffe.online.de.ui.elements;

import com.baustoffe.online.de.dto.ArtikelDTO;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.server.FontAwesome;
import com.vaadin.ui.*;

/**
 * @author Benjamin Wilms (xd98870)
 */
public class ArtikelGrid extends VerticalLayout {

    private Button button, buttonReset;

    private BeanItemContainer<ArtikelDTO> bic = new BeanItemContainer<>(ArtikelDTO.class);

    public ArtikelGrid() {
        button = new Button("Daten laden...");
        button.setIcon(FontAwesome.REFRESH);

        buttonReset = new Button("Reset...");
        buttonReset.setIcon(FontAwesome.ERASER);
        buttonReset.setVisible(false);

        HorizontalLayout hLay = new HorizontalLayout();
        hLay.addComponent(button);
        hLay.addComponent(buttonReset);

        addComponent(hLay);

        Grid grid = new Grid();
        grid.setContainerDataSource(bic);
        grid.setColumns("id", "mandant", "timestamp", "name", "preis", "anzahlBestand");
        grid.setColumnOrder("id", "mandant", "timestamp", "name", "preis", "anzahlBestand");
        grid.getColumn("id").setExpandRatio(1);
        grid.getColumn("name").setExpandRatio(3);
        grid.getColumn("preis").setExpandRatio(2);
        grid.getColumn("anzahlBestand").setExpandRatio(2);
        grid.getColumn("timestamp").setExpandRatio(3);
        grid.getColumn("mandant").setExpandRatio(3);
        grid.getColumn("mandant").setHeaderCaption("System");
        grid.setSizeFull();

        addComponent(grid);
        setComponentAlignment(grid, Alignment.MIDDLE_CENTER);

        setExpandRatio(hLay, 1);
        setExpandRatio(grid, 10);
        setSizeFull();
    }

    public BeanItemContainer<ArtikelDTO> getBic() {
        return bic;
    }

    public Button getButtonRefresh() {
        return button;
    }

    public Button getButtonReset() {
        return buttonReset;
    }
}