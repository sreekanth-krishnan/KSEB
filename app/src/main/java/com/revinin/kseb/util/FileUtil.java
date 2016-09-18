package com.revinin.kseb.util;

import android.content.Context;

import java.io.IOException;
import java.io.InputStream;

/**
 * Created by USER on 17-09-2016.
 */

public class FileUtil {
    public static String loadJSONFromAsset(Context context) throws IOException {
        String json = null;
        try {

            InputStream is = context.getAssets().open("packages.json");

            int size = is.available();

            byte[] buffer = new byte[size];

            is.read(buffer);

            is.close();

            json = new String(buffer, "UTF-8");


        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;

    }
}
