package com.group11.cmpt276_project.utils;

import android.content.Context;

import java.io.IOException;
import java.io.InputStream;

public class Utils {

    public  static String getJsonFromAssets(Context context, String fileName) throws IOException {
        InputStream inputStream = context.getAssets().open(fileName);

        int size = inputStream.available();

        byte[] buffer = new byte[size];
        inputStream.read(buffer);
        inputStream.close();

        return new String(buffer, Constants.ENCODING);
    }
}
