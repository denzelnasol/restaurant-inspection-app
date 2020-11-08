package com.group11.cmpt276_project.utils;

import android.content.Context;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;

/*
Various utility functions that are useful such as loading json
 */
public class Utils {

    public static String readJsonFromAssets(Context context, String fileName) throws IOException {
        InputStream inputStream = context.getAssets().open(fileName);

        int size = inputStream.available();

        byte[] buffer = new byte[size];
        inputStream.read(buffer);
        inputStream.close();

        return new String(buffer, Constants.ENCODING);
    }

    public static void writeJSONToStorage(Context context, String fileName, String toWrite) throws IOException {
        File file = new File(context.getFilesDir(), fileName);
        try (FileWriter fileWriter = new FileWriter(file);
             BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);) {
            bufferedWriter.write(toWrite);
        }
    }

    public static String readJSONFromStorage(Context context, String fileName) throws IOException {
        File file = new File(context.getFilesDir(), fileName);

        try (FileReader fileReader = new FileReader(file);
             BufferedReader bufferedReader = new BufferedReader(fileReader)) {
            StringBuilder stringBuilder = new StringBuilder();

            String line = bufferedReader.readLine();
            while (line != null) {
                stringBuilder.append(line);
                line = bufferedReader.readLine();
            }

            return stringBuilder.toString();
        }
    }


}
