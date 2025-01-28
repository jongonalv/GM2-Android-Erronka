package com.ikaslea.komertzialakapp.models;

import com.j256.ormlite.dao.ForeignCollection;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.field.ForeignCollectionField;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Objects;

@XStreamAlias("Komerziala")

public class Komerziala implements DbEntity {

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
    private String pasahitza;

    @ForeignCollectionField(eager = false)
    private ForeignCollection<Bazkidea> bazkideak;

    @ForeignCollectionField(eager = false)
    private ForeignCollection<Bisita> bisitak;

    public Komerziala() {
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

    public String getPasahitza() {
        return pasahitza;
    }

    public void setPasahitza(String pasahitza) {
        this.pasahitza = pasahitza;
    }

    public Collection<Bazkidea> getBazkideak() {
        return bazkideak;
    }

    public void setBazkideak(ForeignCollection<Bazkidea> bazkideak) {
        this.bazkideak = bazkideak;
    }

    public Collection<Bisita> getBisitak() {
        return bisitak;
    }

    public void setBisitak(ForeignCollection<Bisita> bisitak) {
        this.bisitak = bisitak;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Komerziala that = (Komerziala) o;
        return id == that.id &&
                Objects.equals(izena, that.izena) &&
                Objects.equals(email, that.email) &&
                Objects.equals(telefonoa, that.telefonoa) &&
                Objects.equals(pasahitza, that.pasahitza);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, izena, email, telefonoa, pasahitza);
    }
}
