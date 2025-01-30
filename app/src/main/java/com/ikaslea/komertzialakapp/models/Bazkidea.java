package com.ikaslea.komertzialakapp.models;

import com.ikaslea.komertzialakapp.models.enums.BazkideMota;
import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.field.ForeignCollectionField;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;
import com.thoughtworks.xstream.annotations.XStreamOmitField;

import java.io.Serializable;
import java.util.Collection;
import java.util.Objects;

@XStreamAlias("Bazkidea")

public class Bazkidea implements DbEntity, Serializable {

    @XStreamAsAttribute
    @DatabaseField(generatedId = true)
    private int id;

    @DatabaseField
    private String izena;

    @DatabaseField
    private String email;

    @DatabaseField
    private String telefonoa;

    @DatabaseField
    private String helbidea;

    @DatabaseField(dataType = DataType.STRING)
    private String bazkideMota;

    @DatabaseField(foreign = true, foreignAutoRefresh = true)
    private Komerziala komerziala;

    @XStreamOmitField
    @ForeignCollectionField(eager = false)
    private Collection<Bisita> bisitak;

    public Collection<Bisita> getBisitak() {
        return bisitak;
    }

    public void setBisitak(Collection<Bisita> bisitak) {
        this.bisitak = bisitak;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Bazkidea() {
    }

    public int getId() {
        return id;
    }

    public String getIzena() {
        return izena;
    }

    public void setIzena(String izena) {
        this.izena = izena;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelefonoa() {
        return telefonoa;
    }

    public void setTelefonoa(String telefonoa) {
        this.telefonoa = telefonoa;
    }

    public String getHelbidea() {
        return helbidea;
    }

    public void setHelbidea(String helbidea) {
        this.helbidea = helbidea;
    }

    public BazkideMota getBazkideMota() {
        return BazkideMota.fromString(bazkideMota);
    }

    public void setBazkideMota(BazkideMota bazkideMota) {
        this.bazkideMota = bazkideMota.name();
    }

    protected void setBazkideMota(String bazkideMota) {
        this.bazkideMota = bazkideMota;
    }

    public Komerziala getKomerziala() {
        return komerziala;
    }

    public void setKomerziala(Komerziala komerziala) {
        this.komerziala = komerziala;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Bazkidea bazkidea = (Bazkidea) o;
        return id == bazkidea.id &&
                Objects.equals(izena, bazkidea.izena) &&
                Objects.equals(email, bazkidea.email) &&
                Objects.equals(telefonoa, bazkidea.telefonoa) &&
                Objects.equals(helbidea, bazkidea.helbidea) &&
                bazkideMota.equals(bazkidea.bazkideMota) && Objects.equals(komerziala, bazkidea.komerziala);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, izena, email, telefonoa, helbidea, bazkideMota, komerziala);
    }
}
