package com.ikaslea.komertzialakapp;

import android.app.Application;
import android.content.Context;
import android.os.Environment;
import android.util.Log;

import com.ikaslea.komertzialakapp.models.Artikuloa;
import com.ikaslea.komertzialakapp.models.Bazkidea;
import com.ikaslea.komertzialakapp.models.Bisita;
import com.ikaslea.komertzialakapp.models.Eskaera;
import com.ikaslea.komertzialakapp.models.EskaeraArtikuloa;
import com.ikaslea.komertzialakapp.models.Komerziala;
import com.ikaslea.komertzialakapp.models.enums.BazkideMota;
import com.ikaslea.komertzialakapp.models.enums.Egoera;
import com.ikaslea.komertzialakapp.utils.DBManager;
import com.ikaslea.komertzialakapp.utils.XMLManager;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * hasiran datubase hasirazteko erabiltzen da eta probaratako datuak kargatzeko
 */
public class AppInit extends Application {

    private static final String TAG = "AppInit";

    private static DBManager dbManager;

    @Override
    public void onCreate() {
        super.onCreate();
        DBManager.inizializatu(this);

        dbManager = DBManager.getInstance();
        dbManager.deleteAll();
        if (!Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())) {
            Log.e(TAG, "Almacenamiento externo no disponible.");
            return;
        }

        File externalDir = getExternalFilesDir(null);
        if (externalDir == null) {
            Log.e(TAG, "No se pudo obtener el directorio externo de la app.");
            return;
        }

        File baseDir = new File(externalDir, "datos");

        if (!baseDir.exists()) {
            if (!baseDir.mkdirs()) {
                Log.e(TAG, "No se pudo crear el directorio 'datos'.");
                return;
            }
        }

        File komertzialakFile = new File(baseDir, "komerzialak.xml");

        List<Komerziala> komerzialak = fromXml(komertzialakFile);
        List<Bazkidea> bazkideak = fromXml(new File(baseDir, "bazkideak.xml"));
        List<Artikuloa> artikuloaList = fromXml(new File(baseDir, "artikuluak.xml"));


        for (Komerziala komerziala : komerzialak) {
            dbManager.save(komerziala);
        }

        komerzialak = dbManager.getAll(Komerziala.class);

        for (Bazkidea bazkidea : bazkideak) {
            bazkidea.setKomerziala(komerzialak.get(0));
            dbManager.save(bazkidea);
        }

        for (Artikuloa artikuloa : artikuloaList) {
            dbManager.save(artikuloa);
        }

        bazkideak = dbManager.getAll(Bazkidea.class);
        artikuloaList = dbManager.getAll(Artikuloa.class);

        List<Bisita> bisitaList = createTestBisitaList(komerzialak, bazkideak);
        for (Bisita bisita : bisitaList) {
            dbManager.save(bisita);
        }

        List<Eskaera> eskaeraList = createEskaeraList(bazkideak, artikuloaList);

        for (Eskaera eskaera : eskaeraList) {
            dbManager.save(eskaera);
        }

    }

    private <T> List<T> fromXml(File xml) {
        FileInputStream inputStream = null;
        StringBuilder stringBuilder = new StringBuilder();


        try {
            inputStream = new FileInputStream(xml);
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, StandardCharsets.UTF_8);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                stringBuilder.append(line);
            }
            return XMLManager.getInstance().fromXML(stringBuilder.toString());
        } catch (Exception e) {
            Log.e(TAG, "Error al leer el archivo XML", e);
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (Exception e) {
                    Log.e(TAG, "Error al cerrar el archivo XML", e);
                }
            }
        }
        throw new RuntimeException("El archivo no existe o no se pudo leer");
    }


    // TEST BAT PROBATZEKO ADAPTER-A, DATUAK EZ DIRA HEMENDIK LORTUKO
    private List<Bisita> createTestBisitaList(List<Komerziala> komerzialak, List<Bazkidea> bazkideak) {
        List<Bisita> bisitaList = new ArrayList<>();

        Bisita bisita1 = new Bisita();
        bisita1.setHasieraData(LocalDateTime.of(2025, 1, 10, 9, 0));
        bisita1.setBukaeraData(LocalDateTime.of(2025, 1, 10, 10, 0));
        bisita1.setBisitarenHelburua("Bezeroarekin hasierako bilera");
        bisita1.setHelbidea("Kale Nagusia 123, Bilbo");
        bisita1.setObserbazioak("Bezeroa interesatuta dago premium tresnerian");
        bisita1.setEginda(true);
        bisita1.setBazkidea(bazkideak.get(0));
        bisita1.setKomerzila(komerzialak.get(0));

        Bisita bisita2 = new Bisita();
        bisita2.setHasieraData(LocalDateTime.of(2025, 1, 15, 11, 30));
        bisita2.setBukaeraData(LocalDateTime.of(2025, 1, 15, 12, 30));
        bisita2.setBisitarenHelburua("Katalogo berria aurkeztea");
        bisita2.setHelbidea("Askatasun etorbidea 45, Donostia");
        bisita2.setObserbazioak("Bezeroak deskontuak eskatu ditu bolumen handietarako");
        bisita2.setEginda(false);
        bisita2.setBazkidea(bazkideak.get(1));
        bisita2.setKomerzila(komerzialak.get(0));

        Bisita bisita3 = new Bisita();
        bisita3.setHasieraData(LocalDateTime.of(2025, 1, 20, 14, 0));
        bisita3.setBukaeraData(LocalDateTime.of(2025, 1, 20, 15, 0));
        bisita3.setBisitarenHelburua("Urteko kontratua negoziatzea");
        bisita3.setHelbidea("Kolon pasealekua 78, Gasteiz");
        bisita3.setObserbazioak("Bezeroa interesatuta dago epe luzerako akordio batean");
        bisita3.setEginda(false);
        bisita3.setBazkidea(bazkideak.get(2));
        bisita3.setKomerzila(komerzialak.get(0));

        Bisita bisita4 = new Bisita();
        bisita4.setHasieraData(LocalDateTime.of(2025, 1, 25, 16, 30));
        bisita4.setBukaeraData(LocalDateTime.of(2025, 1, 25, 17, 30));
        bisita4.setBisitarenHelburua("Bezeroaren gogobetetze jarraipena");
        bisita4.setHelbidea("Gran Vía kalea 56, Bilbo");
        bisita4.setObserbazioak("Bezeroak entrega arazoak aipatu ditu");
        bisita4.setEginda(true);
        bisita4.setBazkidea(bazkideak.get(3));
        bisita4.setKomerzila(komerzialak.get(0));

        Bisita bisita5 = new Bisita();
        bisita5.setHasieraData(LocalDateTime.of(2025, 1, 30, 10, 0));
        bisita5.setBukaeraData(LocalDateTime.of(2025, 1, 30, 11, 0));
        bisita5.setBisitarenHelburua("Produktu berrien inguruko prestakuntza");
        bisita5.setHelbidea("Plaza Berria 23, Iruñea");
        bisita5.setObserbazioak("Produktuen laginak entregatu dira");
        bisita5.setEginda(true);
        bisita5.setBazkidea(bazkideak.get(4));
        bisita5.setKomerzila(komerzialak.get(0));

        bisitaList.add(bisita1);
        bisitaList.add(bisita2);
        bisitaList.add(bisita3);
        bisitaList.add(bisita4);
        bisitaList.add(bisita5);

        return bisitaList;
    }




    private List<Eskaera> createEskaeraList(List<Bazkidea> bazkideaList, List<Artikuloa> artikuloaList) {
        List<Eskaera> eskaeraList = new ArrayList<>();

        Eskaera eskaera1 = new Eskaera(
                "Calle Mayor 12, Bilbao",
                LocalDateTime.of(2025, 1, 10, 9, 30),
                Egoera.PRESTATZEN,
                bazkideaList.get(0)
        );

        Eskaera eskaera2 = new Eskaera(
                "Avenida Libertad 5, Donostia",
                LocalDateTime.of(2025, 2, 5, 14, 0),
                Egoera.PRESTATUTA,
                bazkideaList.get(1)
        );

        Eskaera eskaera3 = new Eskaera(
                "Calle San Juan 34, Vitoria",
                LocalDateTime.of(2025, 3, 20, 11, 15),
                Egoera.BIDALITA,
                bazkideaList.get(2)
        );

        Eskaera eskaera4 = new Eskaera(
                "Plaza Nueva 8, Bilbao",
                LocalDateTime.of(2025, 4, 12, 16, 45),
                Egoera.BUKATUTA,
                bazkideaList.get(3)
        );

        Eskaera eskaera5 = new Eskaera(
                "Paseo de la Concha 15, Donostia",
                LocalDateTime.of(2025, 5, 8, 10, 30),
                Egoera.PRESTATZEN,
                bazkideaList.get(4)
        );

        // Eskaerak gehitu
        eskaeraList.add(eskaera1);
        eskaeraList.add(eskaera2);
        eskaeraList.add(eskaera3);
        eskaeraList.add(eskaera4);
        eskaeraList.add(eskaera5);

        return eskaeraList;
    }
}