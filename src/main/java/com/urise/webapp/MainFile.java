package com.urise.webapp;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class MainFile {
    public static void main(String[] args) {
        System.out.println(printCatalog(Paths.get("E:/Java/basejava/src/main/java")));
    }

    public static String printCatalog(Path catalogPath) {
        StringBuilder builder = new StringBuilder();
        try {
            builder.append(catalogPath.getFileName()).append('\n');
            Files
                    .list(catalogPath)
                    .filter(Files::isDirectory)
                    .forEach(path -> builder.append('\t').append(printCatalog(path)).append('\n'));
            Files
                    .list(catalogPath)
                    .filter(Files::isRegularFile)
                    .forEach(path -> builder.append('\t').append('\t').append("--").append(path.getFileName()).append('\n'));
        } catch (IOException e) {
            throw new RuntimeException("Error", e);
        }
        return builder.toString();
    }
}