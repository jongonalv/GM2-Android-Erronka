package com.ikaslea.komertzialakapp.utils;

import android.content.Context;

import com.ikaslea.komertzialakapp.models.Artikuloa;
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
        xstream.processAnnotations(Artikuloa.class);
        xstream.alias("Artikuloak", java.util.List.class);
        xstream.alias("Artikuloa", Artikuloa.class);
        xstream.allowTypes(new Class[]{Artikuloa.class, java.util.List.class});
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

    public String XMLKargatuFitxategitik(Context context) throws IOException {
        InputStream is = context.getResources().openRawResource(R.raw.artikuluak);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        int len;
        while ((len = is.read(buffer)) != -1) {
            baos.write(buffer, 0, len);
        }
        is.close();
        return baos.toString("UTF-8");
    }
}
