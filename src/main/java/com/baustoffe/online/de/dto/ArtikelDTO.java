package com.baustoffe.online.de.dto;

import com.baustoffe.online.de.entities.MandantTyp;

/**
 * @author Benjamin Wilms (xd98870)
 */
public class ArtikelDTO {

    private Long id;

    private String name;

    private Double preis;

    private Long anzahlBestand;

    private String mandant;

    private String timestamp;

    public ArtikelDTO(Long anzahlBestand, long id, String name, Double preis, MandantTyp mandant) {
        this.anzahlBestand = anzahlBestand;
        this.id = id;
        this.name = name;
        this.preis = preis;
        this.mandant = mandant.name();
    }

    public ArtikelDTO() {
    }

    public Long getAnzahlBestand() {
        return anzahlBestand;
    }

    public void setAnzahlBestand(Long anzahlBestand) {
        this.anzahlBestand = anzahlBestand;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getPreis() {
        return preis;
    }

    public void setPreis(Double preis) {
        this.preis = preis;
    }

    public String getMandant() {
        return mandant;
    }

    public void setMandant(String mandant) {
        this.mandant = mandant;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }
}
