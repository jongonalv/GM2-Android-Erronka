package com.ikaslea.komertzialakapp.models;

import com.ikaslea.komertzialakapp.models.enums.Egoera;
import com.ikaslea.komertzialakapp.utils.LocalDateTimeLongType;
import com.j256.ormlite.dao.ForeignCollection;
import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.field.ForeignCollectionField;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

@XStreamAlias("Eskaera")
public class Eskaera implements DbEntity, Serializable {

    @XStreamAsAttribute
    @DatabaseField(generatedId = true)
    private int id;

    @DatabaseField(persisterClass = LocalDateTimeLongType.class)
    private LocalDateTime eskaeraData;

    @DatabaseField(foreign = true, foreignAutoRefresh = true)
    private Bazkidea bazkidea;

    /**
     * TODO: cambiar konzeptua to helbidea
     */
    @DatabaseField
    private String kontzeptua = "";

    @DatabaseField(dataType = DataType.STRING)
    private String egoera = Egoera.PRESTATZEN.name();

    @ForeignCollectionField(eager = true)
    private ForeignCollection<EskaeraArtikuloa> eskaeraArtikuloak;

    public ForeignCollection<EskaeraArtikuloa> getEskaeraArtikuloak() {
        return eskaeraArtikuloak;
    }

    private double guztira = 0;

    public Eskaera() {
    }

    public Eskaera(String kontzeptua, LocalDateTime eskaeraData, Egoera egoera, Bazkidea bazkidea) {

        this.egoera = egoera.name();
        this.kontzeptua = kontzeptua;
        this.bazkidea = bazkidea;
        this.eskaeraData = eskaeraData;
    }

    public void setEskaeraArtikuloak(ForeignCollection<EskaeraArtikuloa> eskaeraArtikuloak) {
        this.eskaeraArtikuloak = eskaeraArtikuloak;
    }

    public void addEskaeraArtikuloa(EskaeraArtikuloa eskaeraArtikuloa) {
        this.eskaeraArtikuloak.add(eskaeraArtikuloa);
    }

    public double getGuztira() {
        return guztira;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public LocalDateTime getEskaeraData() {
        return eskaeraData;
    }

    public void setEskaeraData(LocalDateTime eskaeraData) {
        this.eskaeraData = eskaeraData;
    }

    public Bazkidea getBazkidea() {
        return bazkidea;
    }

    public void setBazkidea(Bazkidea bazkidea) {
        this.bazkidea = bazkidea;
    }

    public String getKontzeptua() {
        return kontzeptua;
    }

    public void setKontzeptua(String kontzeptua) {
        this.kontzeptua = kontzeptua;
    }

    public Egoera getEgoera() {
        return Egoera.valueOf(egoera);
    }

    public void setEgoera(Egoera egoera) {
        this.egoera = egoera.name();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Eskaera eskaera = (Eskaera) o;
        return id == eskaera.id &&
                Double.compare(guztira, eskaera.guztira) == 0 &&
                Objects.equals(eskaeraData, eskaera.eskaeraData) &&
                Objects.equals(bazkidea, eskaera.bazkidea) &&
                Objects.equals(kontzeptua, eskaera.kontzeptua) &&
                egoera.equals(eskaera.egoera);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, eskaeraData, bazkidea, kontzeptua, egoera, guztira);
    }

    public void setGuztira(double guztira) {
    }
}

