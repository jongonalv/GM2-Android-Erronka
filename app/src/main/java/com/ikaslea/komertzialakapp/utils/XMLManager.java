package com.ikaslea.komertzialakapp.utils;

import android.content.Context;

import com.ikaslea.komertzialakapp.models.Artikuloa;
import com.ikaslea.komertzialakapp.models.Bazkidea;
import com.ikaslea.komertzialakapp.models.Komerziala;
import com.ikaslea.komertzialakapp.utils.Const;
import com.thoughtworks.xstream.XStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import com.ikaslea.komertzialakapp.R;

public class XMLManager {

    private static final XMLManager instance = new XMLManager();
    private final XStream xstream = new XStream();

    public static XMLManager getInstance() {
        return instance;
    }

    public XMLManager() {
        // Configura XStream para manejar la clase Artikuloa
        xstream.processAnnotations(Artikuloa.class);
        xstream.alias("Artikuloak", List.class);
        xstream.alias("Artikuloa", Artikuloa.class);

        // Configura XStream para manejar la clase Komerziala
        xstream.processAnnotations(Komerziala.class);
        xstream.alias("Komerzialak", List.class);
        xstream.alias("Komerziala", Komerziala.class);

        xstream.processAnnotations(Bazkidea.class);
        xstream.alias("Bazkideak", List.class);
        xstream.alias("Bazkidea", Bazkidea.class);

        // Permite los tipos necesarios
        xstream.allowTypes(new Class[]{Artikuloa.class, Komerziala.class, Bazkidea.class, List.class});
    }

    public String toXML(Object object) {
        return xstream.toXML(object);
    }

    @SuppressWarnings("unchecked")
    public <T> List<T> fromXML(String xml) {
        Object result = xstream.fromXML(xml);
        if (result instanceof List) {
            return (List<T>) result;
        } else {
            return List.of((T) result);
        }
    }
}