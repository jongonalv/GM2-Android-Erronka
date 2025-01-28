package com.ikaslea.komertzialakapp.utils;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

import com.ikaslea.komertzialakapp.models.Bazkidea;
import com.ikaslea.komertzialakapp.models.*;
import com.ikaslea.komertzialakapp.models.enums.BazkideMota;
import com.ikaslea.komertzialakapp.models.enums.Egoera;

class DBManagerTest {

    private static List<DbEntity> testDataList;

    @BeforeEach
    void setUp() {

        DBManager dbManager = DBManager.getInstance();
        dbManager.deleteAll();

        testDataList = Arrays.asList( createBazkidea(), createEskaera(), createBisita(), createKomerziala());
    }

    private Eskaera createEskaera() {
        Eskaera eskaera = new Eskaera();
        eskaera.setKontzeptua("kontzeptua");
        eskaera.setEgoera(Egoera.BIDALITA);
        eskaera.setBazkidea(null);
        eskaera.setId(1);
        return eskaera;
    }

    private Bazkidea createBazkidea() {
        Bazkidea bazkidea = new Bazkidea();
        bazkidea.setIzena("Izena");
        bazkidea.setHelbidea("Helbidea");
        bazkidea.setTelefonoa("123456789");
        bazkidea.setEmail("email");
        bazkidea.setBazkideMota(BazkideMota.REKURRENTEA);
        return bazkidea;
    }

    private Bisita createBisita() {
        Bisita bisita = new Bisita();
        bisita.setBazkidea(createBazkidea());
        bisita.setHelbidea("Helbidea");
        bisita.setEginda(false);
        bisita.setBisitarenHelburua("Imanoli ipurditik eman");
        bisita.setBukaeraData(LocalDateTime.parse("2025-01-27T10:29:50"));
        bisita.setHasieraData(LocalDateTime.parse("2025-01-27T10:29:50"));
        return bisita;
    }

    private Komerziala createKomerziala() {
        Komerziala komerziala = new Komerziala();
        komerziala.setIzena("Izena");
        komerziala.setEmail("email");
        komerziala.setTelefonoa("123456789");
        komerziala.setPasahitza("pasahitza");
        return komerziala;
    }

    @Test
    void save() {
        DBManager dbManager = DBManager.getInstance();
        for (Object object : testDataList) {
            dbManager.save(object);
        }
    }

    @Test
    void getById() {
        DBManager dbManager = DBManager.getInstance();
        for (DbEntity object : testDataList) {
            dbManager.save(object);
            Object objectFromDB = dbManager.getById(object.getClass(), object.getId());
            assertEquals(object, objectFromDB);
        }
    }

    @Test
    void delete() {
        DBManager dbManager = DBManager.getInstance();
        for (DbEntity object : testDataList) {
            dbManager.save(object);
            dbManager.delete(object);
            Object objectFromDB = dbManager.getById(object.getClass(), object.getId());
            assertNull(objectFromDB);
        }
    }

    @Test
    void getAll() {
        DBManager dbManager = DBManager.getInstance();
        for (int i = 0; i < testDataList.size(); i++) {
            Object object = testDataList.get(i);
            dbManager.save(object);
            List<Object> objectsFromDB = dbManager.getAll(object.getClass());
            assertEquals(object, objectsFromDB.get(0));
        }
    }
}