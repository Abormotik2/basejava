package com.urise.webapp;

import java.io.File;

public class MainFile {

    public static void main(String[] args) {
        printCatalog(new File("E:/Java/basejava/src/main/java/com/urise/webapp"));
    }

    private static void printCatalog(File directory) {

        if (!directory.isDirectory()) {
            throw new IllegalArgumentException("Wrong argument!");
        }

        System.out.println(directory.getName());
        File[] files = directory.listFiles();
        if (files == null) {
            return;
        }
        for (File file : files) {
            if (file.isDirectory()) {
                printCatalog(file);
            } else {
                System.out.println(file.getName());
            }
        }
    }
}