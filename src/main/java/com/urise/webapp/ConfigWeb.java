package com.urise.webapp;

import com.urise.webapp.storage.SqlStorage;
import com.urise.webapp.storage.Storage;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ConfigWeb {
    private static final String PROPS = "/resumesWeb.properties";
    private static final ConfigWeb INSTANCE = new ConfigWeb();

    private final File storageWebDir;
    private final Storage storage;

    public static ConfigWeb get() {
        return INSTANCE;
    }

    private ConfigWeb() {
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
}