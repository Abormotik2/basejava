package com.urise.webapp;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class MainFile {
    public static void main(String[] args) {
        printCatalog(Paths.get("E:/Java/basejava/src/main/java"));
    }

    public static void printCatalog(Path catalogPath) {
        try {
            Files
                    .walk(catalogPath)
                    .forEach(path -> System.out.println(path.getFileName()));
        } catch (IOException e) {
            throw new RuntimeException("Error", e);
        }
    }
}