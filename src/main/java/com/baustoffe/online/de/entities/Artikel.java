package com.baustoffe.online.de.entities;

import javax.persistence.*;

/**
 * @author Benjamin Wilms (xd98870)
 */
@Entity
public class Artikel {

    @Id
    @GeneratedValue
    private Long id;

    @Enumerated(EnumType.STRING)
    private MandantTyp mandant;

    private String name;

    private String beschreibung;

    private Double preis;

    private Long anzahlBestand;

    public Artikel() { // JPA only
    }

    public Artikel(Long anzahlBestand, String name, String beschreibung, Double preis, MandantTyp mandant) {
        this.anzahlBestand = anzahlBestand;
        this.beschreibung = beschreibung;
        this.name = name;
        this.preis = preis;
        this.mandant = mandant;
    }

    public Long getAnzahlBestand() {
        return anzahlBestand;
    }

    public void setAnzahlBestand(Long anzahlBestand) {
        this.anzahlBestand = anzahlBestand;
    }

    public String getBeschreibung() {
        return beschreibung;
    }

    public void setBeschreibung(String beschreibung) {
        this.beschreibung = beschreibung;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
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

    public MandantTyp getMandant() {
        return mandant;
    }

    public void setMandant(MandantTyp mandant) {
        this.mandant = mandant;
    }
}
