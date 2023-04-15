package com.javaops.webapp;

import java.io.File;
import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;

public class MainFile {
    public static void out(String filePath) {
        //https://www.frolov-lib.ru/programming/javasamples/vol7/vol7_30/index.html
        File file = new File(filePath);
        String[] list = file.list();
        for (int i = 0; i < list.length; i++) {
            File f1 = new File(filePath + File.separator + list[i]);
            if (f1.isFile())
                System.out.println(filePath + File.separator + list[i]);
            else {
                out(filePath + File.separator + list[i]);
            }
        }
    }


    public static void main(String[] args) throws IOException {
        String filePath = "C:\\Users\\semib\\OneDrive\\Desktop\\projects\\basejava";
        File dir = new File(filePath);
        System.out.println(dir.isDirectory());
        String[] list = dir.list();
        if (list != null) {
            //рекурсивный обход
            out(filePath);
        }
        //рекурсия с отступами https://ru.stackoverflow.com/questions/636312/%D0%92%D1%8B%D0%B2%D0%BE%D0%B4-%D0%B8%D0%B5%D1%80%D0%B0%D1%80%D1%85%D0%B8%D0%B8-%D0%BF%D0%B0%D0%BF%D0%BA%D0%B8
        Path parent = Paths.get(filePath);
        Files.walkFileTree(parent, new SimpleFileVisitor<Path>() {
            @Override
            public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {
                int count = dir.getNameCount() - parent.getNameCount() + 1;
                count += dir.getFileName().toString().length();

                String text = String.format("%" + count + "s", dir.getFileName());
                text = text.replaceAll("[\\s]", "-");
                System.out.println(text);
                return FileVisitResult.CONTINUE;
            }
        });
    }
}
