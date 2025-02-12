package com.ikaslea.komertzialakapp.utils;

import android.content.Context;
import android.os.Environment;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class FileManager {

    /**
     * XML edukia fitxategi batean gordetzeko metodoa.
     *
     * @param context Aplikazioaren testuingurua (context)
     * @param xmlContent Gorde beharreko XML edukia
     * @param fileName Sortuko den fitxategiaren izena
     * @return Gordetzea arrakastatsua izan bada, true; bestela, false
     */
    public static boolean saveXMLToFile(Context context, String xmlContent, String fileName) {
        // Kanpoko biltegiratzea erabil daitekeen egiaztatu
        if (!isExternalStorageWritable()) {
            return false;
        }

        // Fitxategiaren kokapena zehaztu
        File file = new File(context.getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS), fileName);

        try (FileOutputStream fos = new FileOutputStream(file)) {
            // XML edukia fitxategira idatzi
            fos.write(xmlContent.getBytes());
            return true;
        } catch (IOException e) {
            e.printStackTrace(); // Errorea inprimatu
            return false;
        }
    }

    /**
     * Kanpoko biltegiratzea idazteko moduan dagoen egiaztatzen du.
     *
     * @return Kanpoko biltegiratzea erabilgarri badago, true; bestela, false
     */
    private static boolean isExternalStorageWritable() {
        String state = Environment.getExternalStorageState();
        return Environment.MEDIA_MOUNTED.equals(state);
    }
}
