package com.ikaslea.komertzialakapp.utils;

import com.ikaslea.komertzialakapp.models.*;

import java.lang.reflect.Type;

public class Const {

    public final static String DATABASE_URL = "jdbc:sqlite:komertzioa.db";

    //Datubaseako tablen modeloen klaseak
    public final static Class<?>[] TABLES = {
            Artikuloa.class,
            Bazkidea.class,
            Bisita.class,
            Eskaera.class,
            EskaeraArtikuloa.class,
            Komerziala.class
    };
}
