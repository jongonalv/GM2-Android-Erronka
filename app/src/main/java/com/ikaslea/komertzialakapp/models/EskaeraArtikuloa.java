package com.ikaslea.komertzialakapp.models;

import com.j256.ormlite.field.DatabaseField;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;
import com.thoughtworks.xstream.annotations.XStreamOmitField;

import java.io.Serializable;
import java.util.Objects;

@XStreamAlias("EskaeraArtikuloa")

public class EskaeraArtikuloa implements DbEntity, Serializable {

    @XStreamAsAttribute
    @DatabaseField(generatedId = true)
    private int id;

    @DatabaseField
    private double guztira;

    @DatabaseField(defaultValue = "1")
    private int kantitatea;

    @DatabaseField(defaultValue = "0")
    private int deskontua;

    @DatabaseField(foreign = true, foreignAutoRefresh = true)
    private Artikuloa artikuloa;

    @XStreamOmitField
    @DatabaseField(foreign = true, foreignAutoRefresh = true)
    private Eskaera eskaera;

    public EskaeraArtikuloa() {
    }

    public Eskaera getEskaera() {
        return eskaera;
    }

    public void setEskaera(Eskaera eskaera) {
        this.eskaera = eskaera;
    }

    public Artikuloa getArtikuloa() {
        return artikuloa;
    }

    public void setArtikuloa(Artikuloa artikuloa) {
        this.artikuloa = artikuloa;
    }

    public int getDeskontua() {
        return deskontua;
    }


    public int getKantitatea() {
        return kantitatea;
    }

    public void setKantitatea(int kantitatea) {
        this.kantitatea = kantitatea;
    }

    public double getGuztira() {
        return guztira;
    }


    public int getId() {
        return id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EskaeraArtikuloa that = (EskaeraArtikuloa) o;
        return id == that.id && Double.compare(guztira, that.guztira) == 0 && kantitatea == that.kantitatea && deskontua == that.deskontua && Objects.equals(artikuloa, that.artikuloa) && Objects.equals(eskaera, that.eskaera);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, guztira, kantitatea, deskontua, artikuloa, eskaera);
    }
}
