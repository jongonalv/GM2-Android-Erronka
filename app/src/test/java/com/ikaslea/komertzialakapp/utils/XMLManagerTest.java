package com.ikaslea.komertzialakapp.utils;

import com.hruiz.models.*;
import com.hruiz.models.enums.BazkideMota;
import com.hruiz.models.enums.Egoera;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class XMLManagerTest {

    private List<TestData> testDataList;

    @BeforeEach
    void setUp() {
        testDataList = Arrays.asList(
                new TestData("<Bazkidea id=\"0\">\n" +
                        "  <izena>Izena</izena>\n" +
                        "  <email>email</email>\n" +
                        "  <telefonoa>123456789</telefonoa>\n" +
                        "  <helbidea>Helbidea</helbidea>\n" +
                        "  <bazkideMota>REKURRENTEA</bazkideMota>\n" +
                        "</Bazkidea>", createBazkidea()),

                new TestData("<Eskaera id=\"1\">\n" +
                        "  <kontzeptua>kontzeptua</kontzeptua>\n" +
                        "  <egoera>BIDALITA</egoera>\n" +
                        "  <guztira>0.0</guztira>\n" +
                        "</Eskaera>", createEskaera()),

                new TestData("<Bisita id=\"0\">\n" +
                        "  <hasieraData>2025-01-27T10:29:50</hasieraData>\n" +
                        "  <bukaeraData>2025-01-27T10:29:50</bukaeraData>\n" +
                        "  <helbidea>Helbidea</helbidea>\n" +
                        "  <bisitarenHelburua>Imanol</bisitarenHelburua>\n" +
                        "  <eginda>false</eginda>\n" +
                        "  <bazkidea id=\"0\">\n" +
                        "    <izena>Izena</izena>\n" +
                        "    <email>email</email>\n" +
                        "    <telefonoa>123456789</telefonoa>\n" +
                        "    <helbidea>Helbidea</helbidea>\n" +
                        "    <bazkideMota>REKURRENTEA</bazkideMota>\n" +
                        "  </bazkidea>\n" +
                        "</Bisita>", createBisita()),

                new TestData("<Komerziala id=\"0\">\n" +
                        "  <izena>Izena</izena>\n" +
                        "  <email>email</email>\n" +
                        "  <telefonoa>123456789</telefonoa>\n" +
                        "  <pasahitza>pasahitza</pasahitza>\n" +
                        "</Komerziala>", createKomerziala())
        );
    }

    private Eskaera createEskaera() {
        var eskaera = new Eskaera();
        eskaera.setKontzeptua("kontzeptua");
        eskaera.setEgoera(Egoera.BIDALITA);
        eskaera.setBazkidea(null);
        eskaera.setId(1);
        return eskaera;
    }

    private Bazkidea createBazkidea() {
        var bazkidea = new Bazkidea();
        bazkidea.setIzena("Izena");
        bazkidea.setHelbidea("Helbidea");
        bazkidea.setTelefonoa("123456789");
        bazkidea.setEmail("email");
        bazkidea.setBazkideMota(BazkideMota.REKURRENTEA);
        return bazkidea;
    }

    private Bisita createBisita() {
        var bisita = new Bisita();
        bisita.setBazkidea(createBazkidea());
        bisita.setBisitarenHelburua("Imanol");
        bisita.setHelbidea("Helbidea");
        bisita.setEginda(false);
        bisita.setBukaeraData(LocalDateTime.parse("2025-01-27T10:29:50"));
        bisita.setHasieraData(LocalDateTime.parse("2025-01-27T10:29:50"));
        return bisita;
    }

    private Komerziala createKomerziala() {
        var komerziala = new Komerziala();
        komerziala.setIzena("Izena");
        komerziala.setEmail("email");
        komerziala.setTelefonoa("123456789");
        komerziala.setPasahitza("pasahitza");
        return komerziala;
    }

    private static class TestData {
        String xml;
        Object object;

        TestData(String xml, Object object) {
            this.xml = xml;
            this.object = object;
        }
    }

@Test
    void toXML() {
        XMLManager xmlManager = XMLManager.getInstance();
        for (TestData testData : testDataList) {
            String xml = xmlManager.toXML(testData.object);
            System.out.println(xml);
            assertEquals(testData.xml, xml);
        }
    }

    @Test
    void fromXML() {
        XMLManager xmlManager = XMLManager.getInstance();
        for (TestData testData : testDataList) {
            Object object = xmlManager.fromXML(testData.xml);
            assertEquals(testData.object, object);
        }
    }
}