package com.ikaslea.komertzialakapp.models;

import com.ikaslea.komertzialakapp.utils.LocalDateTimeLongType;
import com.j256.ormlite.field.DatabaseField;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;

import java.time.LocalDateTime;
import java.util.Objects;

@XStreamAlias("Bisita")

public class Bisita implements DbEntity{

    @XStreamAsAttribute
    @DatabaseField(generatedId = true)
    private int id;

    @DatabaseField(canBeNull = false, persisterClass = LocalDateTimeLongType.class)
    private LocalDateTime hasieraData;

    @DatabaseField(canBeNull = false,persisterClass = LocalDateTimeLongType.class)
    private LocalDateTime bukaeraData;

    @DatabaseField
    private String helbidea;

    @DatabaseField
    private String bisitarenHelburua;

    @DatabaseField
    private String obserbazioak;

    @DatabaseField
    private boolean eginda;

    @DatabaseField(foreign = true, foreignAutoCreate = true, foreignAutoRefresh = true)
    private Bazkidea bazkidea;

    @DatabaseField(foreign = true, foreignAutoCreate = true, foreignAutoRefresh = true)
    private Komerziala komerzila;

    public Bisita() {
    }

    public int getId() {
        return id;
    }

    public LocalDateTime getHasieraData() {
        return hasieraData;
    }

    public void setHasieraData(LocalDateTime hasieraData) {
        this.hasieraData = hasieraData;
    }

    public LocalDateTime getBukaeraData() {
        return bukaeraData;
    }

    public void setBukaeraData(LocalDateTime bukaeraData) {
        this.bukaeraData = bukaeraData;
    }

    public String getHelbidea() {
        return helbidea;
    }

    public void setHelbidea(String helbidea) {
        this.helbidea = helbidea;
    }

    public String getBisitarenHelburua() {
        return bisitarenHelburua;
    }

    public void setBisitarenHelburua(String bisitarenHelburua) {
        this.bisitarenHelburua = bisitarenHelburua;
    }

    public String getObserbazioak() {
        return obserbazioak;
    }

    public void setObserbazioak(String obserbazioak) {
        this.obserbazioak = obserbazioak;
    }

    public boolean isEginda() {
        return eginda;
    }

    public void setEginda(boolean eginda) {
        this.eginda = eginda;
    }

    public Bazkidea getBazkidea() {
        return bazkidea;
    }

    public void setBazkidea(Bazkidea bazkidea) {
        this.bazkidea = bazkidea;
    }

    public Komerziala getKomerzila() {
        return komerzila;
    }

    public void setKomerzila(Komerziala komerzila) {
        this.komerzila = komerzila;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Bisita bisita = (Bisita) o;
        return id == bisita.id &&
                (eginda == bisita.eginda) &&
                Objects.equals(hasieraData, bisita.hasieraData) &&
                Objects.equals(bukaeraData, bisita.bukaeraData) &&
                Objects.equals(helbidea, bisita.helbidea) &&
                bisitarenHelburua.equals(bisita.bisitarenHelburua) &&
                Objects.equals(obserbazioak, bisita.obserbazioak) &&
                Objects.equals(bazkidea, bisita.bazkidea) &&
                Objects.equals(komerzila, bisita.komerzila);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, hasieraData, bukaeraData, helbidea, bisitarenHelburua, obserbazioak, eginda, bazkidea, komerzila);
    }
}
