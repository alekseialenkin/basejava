package com.javaops.webapp;

import java.io.File;

public class MainFile {
    public static void out(String filePath) {
        //https://www.frolov-lib.ru/programming/javasamples/vol7/vol7_30/index.html
        File file = new File(filePath);
        String[] list = file.list();
        for (int i = 0; i < list.length; i++) {
            File f1 = new File(filePath + File.separator + list[i]);
            if (f1.isFile())
                System.out.println(filePath +File.separator + list[i]);
            else {
                out(filePath +File.separator + list[i]);
            }
        }
    }

    public static void main(String[] args) {
        String filePath = "C:\\Users\\semib\\OneDrive\\Desktop\\projects\\basejava";
        File dir = new File(filePath);
        System.out.println(dir.isDirectory());
        String[] list = dir.list();
        if (list != null) {
            //рекурсивный обход
            out(filePath);
        }
    }
}
