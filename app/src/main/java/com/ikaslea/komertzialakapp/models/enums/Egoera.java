package com.ikaslea.komertzialakapp.models.enums;

public enum Egoera {
    BIDALITA,
    PRESTATUTA,
    PRESTATZEN,
    BUKATUTA;

    public static Egoera fromString(String text) {
        for (Egoera b : Egoera.values()) {
            if (b.name().equalsIgnoreCase(text)) {
                return b;
            }
        }
        throw new IllegalArgumentException("No enum constant " + Egoera.class.getCanonicalName() + " with text " + text);
    }
}
