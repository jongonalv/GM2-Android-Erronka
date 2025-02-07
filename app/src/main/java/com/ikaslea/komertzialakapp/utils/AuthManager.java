package com.ikaslea.komertzialakapp.utils;

import com.ikaslea.komertzialakapp.models.Komerziala;

public class AuthManager {

    /*
        Komerziala existitzen den konprobatzen dugu login-a egiteko
     */
    public static Komerziala login(String izena, String pasahitza) {
        DBManager dbManager = DBManager.getInstance();

        Komerziala user = dbManager.getByIzena(izena);

        if (user != null && user.getPasahitza().equals(pasahitza)) {
            return user;
        }

        return null;
    }
}