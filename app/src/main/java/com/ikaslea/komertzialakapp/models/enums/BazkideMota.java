package com.ikaslea.komertzialakapp.models.enums;

public enum BazkideMota {
    BERRIA,
    POTENZIALA,
    REKURRENTEA;

    public static BazkideMota fromString(String text) {
        for (BazkideMota b : BazkideMota.values()) {
            if (b.name().equalsIgnoreCase(text)) {
                return b;
            }
        }
        throw new IllegalArgumentException("No enum constant " + BazkideMota.class.getCanonicalName() + " with text " + text);
    }
}
