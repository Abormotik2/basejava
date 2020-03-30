package com.urise.webapp;

import com.urise.webapp.storage.SqlStorage;
import com.urise.webapp.storage.Storage;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ConfigHeroku {
    private static final String PROPS = "/resumesWeb.properties";
    private static final ConfigHeroku INSTANCE = new ConfigHeroku();

    private final File storageWebDir;
    private final Storage storage;

    public static ConfigHeroku get() {
        return INSTANCE;
    }

    private ConfigHeroku() {
        try (InputStream is = new FileInputStream(PROPS)) {
            Properties props = new Properties();
            props.load(is);
            storageWebDir = new File(props.getProperty("storage.dir"));
            storage = new SqlStorage(props.getProperty("db.url"), props.getProperty("db.user"), props.getProperty("db.password"));
        } catch (IOException e) {
            throw new IllegalStateException("Invalid config file " + PROPS);
        }
    }

    public Storage getStorage() {
        return storage;
    }

    public File getStorageWebDir() {
        return storageWebDir;
    }
}