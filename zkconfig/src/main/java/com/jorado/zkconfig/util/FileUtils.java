package com.jorado.zkconfig.util;

import java.io.*;

public class FileUtils {
    public static void save(String path, String content) {
        File file = new File(path);
        try {
            if (!file.exists()) {
                createDir(file.getParent());
                file.createNewFile();
            }
            PrintWriter writer = new PrintWriter(file, "UTF-8");
            writer.println(content);
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void createDir(String path) {
        File file = new File(path);
        file.mkdirs();
    }

    public static boolean exists(String path) {
        File file = new File(path);
        return file.exists();
    }

    public static String read(String path) {
        StringBuffer str = new StringBuffer("");
        FileInputStream fs;
        InputStreamReader isr;
        try {
            fs = new FileInputStream(path);
            isr = new InputStreamReader(fs, "UTF-8");
            BufferedReader br = new BufferedReader(isr);
            String data = "";
            while ((data = br.readLine()) != null) {
                str.append(data);
            }
            isr.close();
            fs.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return str.toString();
    }
}
