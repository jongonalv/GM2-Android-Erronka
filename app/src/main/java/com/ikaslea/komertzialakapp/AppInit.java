package com.ikaslea.komertzialakapp;

import android.app.Application;

import com.ikaslea.komertzialakapp.models.Artikuloa;
import com.ikaslea.komertzialakapp.models.Bazkidea;
import com.ikaslea.komertzialakapp.models.Bisita;
import com.ikaslea.komertzialakapp.models.Eskaera;
import com.ikaslea.komertzialakapp.models.EskaeraArtikuloa;
import com.ikaslea.komertzialakapp.models.Komerziala;
import com.ikaslea.komertzialakapp.models.enums.BazkideMota;
import com.ikaslea.komertzialakapp.models.enums.Egoera;
import com.ikaslea.komertzialakapp.utils.DBManager;

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

        dbManager = DBManager.getInstance();

        dbManager.deleteAll();

        List<Artikuloa> artikuloaList = createArticulos();
        for (Artikuloa artikuloa : artikuloaList) {
            dbManager.save(artikuloa);
        }

        List<Bisita> bisitaList = createTestBisitaList();
        for (Bisita bisita : bisitaList) {
            dbManager.save(bisita.getKomerzila());
            dbManager.save(bisita.getBazkidea());
            dbManager.save(bisita);
        }

        List<Bazkidea> bazkideaList = dbManager.getAll(Bazkidea.class);
        List<Eskaera> eskaeraList = createEskaeraList(bazkideaList, artikuloaList);

        for (Eskaera eskaera : eskaeraList) {
            dbManager.save(eskaera);
        }

        for (Bazkidea bazkidea : bazkideaList) {
            dbManager.save(bazkidea);
        }
    }

    private List<Komerziala> createKomerzialak() {
        List<Bisita> bisitaList = new ArrayList<>();

        Komerziala komerzila1 = new Komerziala();
        komerzila1.setIzena("Jon Etxebarria");
        komerzila1.setEmail("jon@example.com");
        komerzila1.setTelefonoa("655123456");
        komerzila1.setPasahitza("123");

        Komerziala komerzila2 = new Komerziala();
        komerzila2.setIzena("Maite Zubiri");
        komerzila2.setEmail("maite@example.com");
        komerzila2.setTelefonoa("666234567");
        komerzila2.setPasahitza("123");

        Komerziala komerzila3 = new Komerziala();
        komerzila3.setIzena("Peio Mendizabal");
        komerzila3.setEmail("peio@example.com");
        komerzila3.setTelefonoa("677345678");
        komerzila3.setPasahitza("123");

        Komerziala komerzila4 = new Komerziala();
        komerzila4.setIzena("Irati Garmendia");
        komerzila4.setEmail("irati@example.com");
        komerzila4.setTelefonoa("688456789");
        komerzila4.setPasahitza("123");

        Komerziala komerzila5 = new Komerziala();
        komerzila5.setIzena("Xabier Olaizola");
        komerzila5.setEmail("xabier@example.com");
        komerzila5.setTelefonoa("699567890");
        komerzila5.setPasahitza("123");

        ArrayList<Komerziala> komerzialak = new ArrayList<>();
        komerzialak.add(komerzila1);
        komerzialak.add(komerzila2);
        komerzialak.add(komerzila3);
        komerzialak.add(komerzila4);
        komerzialak.add(komerzila5);

        return komerzialak;
    }

    // TEST BAT PROBATZEKO ADAPTER-A, DATUAK EZ DIRA HEMENDIK LORTUKO
    private List<Bisita> createTestBisitaList() {
        List<Bisita> bisitaList = new ArrayList<>();

        ArrayList<Komerziala> komerzialak = (ArrayList<Komerziala>) createKomerzialak();

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

    private List<Artikuloa> createArticulos(){

        List<Artikuloa> articulos = new ArrayList<>();

        // Categoría: Materiales de construcción
        articulos.add(new Artikuloa(1, "Cemento 25KG", "Materiales de construcción", 6.99, 100.0));
        articulos.add(new Artikuloa(2, "Saco de arena fina 40KG", "Materiales de construcción", 4.99, 100.0));
        articulos.add(new Artikuloa(3, "Yeso 20KG", "Materiales de construcción", 5.50, 100.0));

        // Categoría: Burdindegia (Ferretería)
        articulos.add(new Artikuloa(4, "Tornillo 0,69x2 (100 unidades)", "Burdindegia", 4.99, 1000.0));
        articulos.add(new Artikuloa(5, "Tuerca x100", "Burdindegia", 3.99, 1000.0));

        // Categoría: Eskuzko tresnak (Herramientas manuales)
        articulos.add(new Artikuloa(6, "Destornillador multipunta", "Eskuzko tresnak", 12.50, 150.0));
        articulos.add(new Artikuloa(7, "Llave inglesa", "Eskuzko tresnak", 14.95, 250.0));
        articulos.add(new Artikuloa(8, "Martillo para carpintero", "Eskuzko tresnak", 9.99, 100.0));

        // Categoría: Tresna elektrikoak (Herramientas eléctricas)
        articulos.add(new Artikuloa(9, "Destornillador eléctrico", "Tresna elektrikoak", 74.95, 30.0));
        articulos.add(new Artikuloa(10, "Motosierra eléctrica", "Tresna elektrikoak", 300.0, 15.0));
        articulos.add(new Artikuloa(11, "Taladro eléctrico", "Tresna elektrikoak", 89.99, 10.0));

        return articulos;
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