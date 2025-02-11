package com.ikaslea.komertzialakapp.models;

import com.j256.ormlite.field.DatabaseField;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;
import java.io.Serializable;

@XStreamAlias("Artikuloa")
public class Artikuloa implements DbEntity, Serializable {

    @XStreamAsAttribute
    @DatabaseField(generatedId = true) // ID autogenerado
    private int id;

    @DatabaseField
    private String izena;

    @DatabaseField
    private String kategoria;

    @DatabaseField
    private double prezioa;

    @DatabaseField
    private double stock;

    @DatabaseField // Asegura que ORMLite maneje este campo
    private String imageUrl;

    // --- Getters ---
    public int getId() { return id; }

    public String getIzena() { return izena; }

    public String getKategoria() { return kategoria; }

    public double getPrezioa() { return prezioa; }

    public double getStock() { return stock; }

    public String getImageUrl() { return imageUrl; }

    // --- Setters ---
    public void setKategoria(String kategoria) { this.kategoria = kategoria; }

    public void setStock(double stock) { this.stock = stock; }

    public void setPrezioa(double prezioa) { this.prezioa = prezioa; }

    public void setImageUrl(String imageUrl) { this.imageUrl = imageUrl; }

    // --- Constructores ---
    public Artikuloa() {
        // Constructor vacío necesario para ORMLite y XStream
    }

    public Artikuloa(int id, String izena, String kategoria, double prezioa, double stock, String imageUrl) {
        this.id = id;
        this.izena = izena;
        this.kategoria = kategoria;
        this.prezioa = prezioa;
        this.stock = stock;
        this.imageUrl = imageUrl;
    }
}
