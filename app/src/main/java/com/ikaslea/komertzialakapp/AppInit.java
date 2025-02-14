package com.ikaslea.komertzialakapp;

import android.app.Application;
import android.content.Context;

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

import java.io.File;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * hasiran datubase hasirazteko erabiltzen da eta probaratako datuak kargatzeko
 */
public class AppInit extends Application {

    private static DBManager dbManager;

    @Override
    public void onCreate() {
        super.onCreate();
        DBManager.inizializatu(this);

        File dir = new File("./data");
        if (!dir.exists()) dir.mkdir();


        dbManager = DBManager.getInstance();

        dbManager.deleteAll();

        List<Bisita> bisitaList = createTestBisitaList();
        for (Bisita bisita : bisitaList) {
            dbManager.save(bisita.getKomerzila());
            dbManager.save(bisita.getBazkidea());
            dbManager.save(bisita);
        }

        List<Bazkidea> bazkideaList = dbManager.getAll(Bazkidea.class);
/*        List<Eskaera> eskaeraList = createEskaeraList(bazkideaList, artikuloaList);

        for (Eskaera eskaera : eskaeraList) {
            dbManager.save(eskaera);
        }*/

        for (Bazkidea bazkidea : bazkideaList) {
            dbManager.save(bazkidea);
        }
    }
    private List<Komerziala> komertzialakKargatuXMLBidez(Context context) {
        List<Komerziala> komerzialakList = new ArrayList<>();

        try {
            String xml = XMLManager.getInstance().XMLKargatuFitxategitikKomerzialak(context);
            List<Komerziala> komerzialakFromXML = XMLManager.getInstance().fromXML(xml);

            if (komerzialakFromXML != null) {
                for (Komerziala komerzialaXML : komerzialakFromXML) {
                    Komerziala existingKomerziala = dbManager.getByIzena(komerzialaXML.getIzena());

                    if (existingKomerziala != null) {
                        existingKomerziala.setIzena(komerzialaXML.getIzena());
                        existingKomerziala.setTelefonoa(komerzialaXML.getTelefonoa());
                        existingKomerziala.setPasahitza(komerzialaXML.getPasahitza());

                        dbManager.save(existingKomerziala);
                        komerzialakList.add(existingKomerziala);
                    } else {
                        dbManager.save(komerzialaXML);
                        komerzialakList.add(komerzialaXML);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return komerzialakList;
    }


    // TEST BAT PROBATZEKO ADAPTER-A, DATUAK EZ DIRA HEMENDIK LORTUKO
    private List<Bisita> createTestBisitaList() {
        List<Bisita> bisitaList = new ArrayList<>();

        ArrayList<Komerziala> komerzialak = (ArrayList<Komerziala>) komertzialakKargatuXMLBidez(this);

        Bazkidea bazkidea1 = new Bazkidea();
        bazkidea1.setIzena("Ander Olaizola");
        bazkidea1.setEmail("ander@example.com");
        bazkidea1.setTelefonoa("600123456");
        bazkidea1.setHelbidea("Calle Mayor 12, Bilbao");
        bazkidea1.setBazkideMota(BazkideMota.BERRIA);
        bazkidea1.setKomerziala(komerzialak.get(0));

        Bazkidea bazkidea2 = new Bazkidea();
        bazkidea2.setIzena("Miren Agirre");
        bazkidea2.setEmail("miren@example.com");
        bazkidea2.setTelefonoa("611234567");
        bazkidea2.setHelbidea("Avenida Libertad 5, Donostia");
        bazkidea2.setBazkideMota(BazkideMota.BERRIA);
        bazkidea2.setKomerziala(komerzialak.get(3));

        Bazkidea bazkidea3 = new Bazkidea();
        bazkidea3.setIzena("Iñaki Lertxundi");
        bazkidea3.setEmail("inaki@example.com");
        bazkidea3.setTelefonoa("622345678");
        bazkidea3.setHelbidea("Calle San Juan 34, Vitoria");
        bazkidea3.setBazkideMota(BazkideMota.BERRIA);
        bazkidea3.setKomerziala(komerzialak.get(0));

        Bazkidea bazkidea4 = new Bazkidea();
        bazkidea4.setIzena("Ane Etxeberria");
        bazkidea4.setEmail("ane@example.com");
        bazkidea4.setTelefonoa("633456789");
        bazkidea4.setHelbidea("Plaza Nueva 8, Bilbao");
        bazkidea4.setBazkideMota(BazkideMota.REKURRENTEA);
        bazkidea4.setKomerziala(komerzialak.get(2));

        Bazkidea bazkidea5 = new Bazkidea();
        bazkidea5.setIzena("Jokin Mendizabal");
        bazkidea5.setEmail("jokin@example.com");
        bazkidea5.setTelefonoa("644567890");
        bazkidea5.setHelbidea("Paseo de la Concha 15, Donostia");
        bazkidea5.setBazkideMota(BazkideMota.POTENZIALA);
        bazkidea5.setKomerziala(komerzialak.get(1));

        Bisita bisita1 = new Bisita();
        bisita1.setHasieraData(LocalDateTime.of(2025, 1, 10, 9, 0));
        bisita1.setBukaeraData(LocalDateTime.of(2025, 1, 10, 10, 0));
        bisita1.setBisitarenHelburua("Bezeroarekin hasierako bilera");
        bisita1.setHelbidea("Kale Nagusia 123, Bilbo");
        bisita1.setObserbazioak("Bezeroa interesatuta dago premium tresnerian");
        bisita1.setEginda(true);
        bisita1.setBazkidea(bazkidea1);
        bisita1.setKomerzila(komerzialak.get(0));

        Bisita bisita2 = new Bisita();
        bisita2.setHasieraData(LocalDateTime.of(2025, 1, 15, 11, 30));
        bisita2.setBukaeraData(LocalDateTime.of(2025, 1, 15, 12, 30));
        bisita2.setBisitarenHelburua("Katalogo berria aurkeztea");
        bisita2.setHelbidea("Askatasun etorbidea 45, Donostia");
        bisita2.setObserbazioak("Bezeroak deskontuak eskatu ditu bolumen handietarako");
        bisita2.setEginda(false);
        bisita2.setBazkidea(bazkidea2);
        bisita2.setKomerzila(komerzialak.get(3));

        Bisita bisita3 = new Bisita();
        bisita3.setHasieraData(LocalDateTime.of(2025, 1, 20, 14, 0));
        bisita3.setBukaeraData(LocalDateTime.of(2025, 1, 20, 15, 0));
        bisita3.setBisitarenHelburua("Urteko kontratua negoziatzea");
        bisita3.setHelbidea("Kolon pasealekua 78, Gasteiz");
        bisita3.setObserbazioak("Bezeroa interesatuta dago epe luzerako akordio batean");
        bisita3.setEginda(false);
        bisita3.setBazkidea(bazkidea3);
        bisita3.setKomerzila(komerzialak.get(0));

        Bisita bisita4 = new Bisita();
        bisita4.setHasieraData(LocalDateTime.of(2025, 1, 25, 16, 30));
        bisita4.setBukaeraData(LocalDateTime.of(2025, 1, 25, 17, 30));
        bisita4.setBisitarenHelburua("Bezeroaren gogobetetze jarraipena");
        bisita4.setHelbidea("Gran Vía kalea 56, Bilbo");
        bisita4.setObserbazioak("Bezeroak entrega arazoak aipatu ditu");
        bisita4.setEginda(true);
        bisita4.setBazkidea(bazkidea4);
        bisita4.setKomerzila(komerzialak.get(2));

        Bisita bisita5 = new Bisita();
        bisita5.setHasieraData(LocalDateTime.of(2025, 1, 30, 10, 0));
        bisita5.setBukaeraData(LocalDateTime.of(2025, 1, 30, 11, 0));
        bisita5.setBisitarenHelburua("Produktu berrien inguruko prestakuntza");
        bisita5.setHelbidea("Plaza Berria 23, Iruñea");
        bisita5.setObserbazioak("Produktuen laginak entregatu dira");
        bisita5.setEginda(true);
        bisita5.setBazkidea(bazkidea5);
        bisita5.setKomerzila(komerzialak.get(1));

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