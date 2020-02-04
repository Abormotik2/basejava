package com.urise.webapp;

import java.io.File;

public class MainFile {

    public static void main(String[] args) {
        printCatalog(new File("E:/Java/basejava/src/main/java"), "");
    }

    private static void printCatalog(File directory, String indent) {

        if (!directory.isDirectory()) {
            throw new IllegalArgumentException("Wrong argument!");
        }
        System.out.println(indent + "dir: " + directory.getName());
        File[] files = directory.listFiles();
        if (files == null) {
            return;
        }
        for (File file : files) {
            if (file.isDirectory()) {
                printCatalog(file, indent + "  ");
            } else {
                System.out.println(indent + "   f: " + file.getName());
            }
        }
    }
}