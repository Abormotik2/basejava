package com.urise.webapp;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class MainFile {
    private static List<File> files = new ArrayList<>();

    public static void main(String[] args) {
        printCatalog(files, new File("E:/Java/basejava/src/main/java"));
        for (Object file : files.toArray()) {
            System.out.println(((File) file).getAbsolutePath());
        }
    }

    private static void printCatalog(List<File> source, File parent) {
        if (!source.contains(parent)) {
            source.add(parent);
        }
        File[] listFiles = parent.listFiles();
        if (listFiles == null) {
            return;
        }
        for (File file : listFiles) {
            if (file.isDirectory()) {
                printCatalog(source, file);
            } else {
                if (!source.contains(file)) {
                    source.add(file);
                }
            }
        }
    }
}