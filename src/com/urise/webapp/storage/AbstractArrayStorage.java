package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

public abstract class AbstractArrayStorage implements Storage {
    protected static final int STORAGE_LIMIT = 10_000;

    protected Resume[] storage = new Resume[STORAGE_LIMIT];
    protected int size;
    
    public Resume get(String uuid) {
        int gIndex = getIndex(uuid);
        if (gIndex == -1) {
            System.out.println("Resume not found");
            return null;
        }
        return storage[gIndex];
    }

    protected abstract int getIndex(String uuid);
}