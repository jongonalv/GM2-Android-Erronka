package com.ikaslea.komertzialakapp.utils;

import com.hruiz.models.*;
import com.thoughtworks.xstream.XStream;

import java.util.List;

public class XMLManager {

    private static final XMLManager instance = new XMLManager();

    private final XStream xstream = new XStream();

    public static XMLManager getInstance() {
        return instance;
    }

    private XMLManager() {

        // Tabletako anotaziak prozesatu eta ondoren erabili al direal adierazi
        xstream.processAnnotations(Const.TABLES);
        xstream.allowTypes(Const.TABLES);
    }

    /**
     * Object bat XML-ra bihurtu
     * @param object Object bat
     * @return XML-a
     */
    public String toXML(Object object) {
        return xstream.toXML(object);
    }

    /**
     * XML bat Object bihurtu
     * @param xml XML bat
     * @return xml-etik parseatutako objetua
     * @param <T> Objetu mota
     */
    public <T> Object fromXML(String xml) {
        Object result = xstream.fromXML(xml);
        if (result instanceof List) {
            return result;
        } else {
            return result;
        }
    }

}
