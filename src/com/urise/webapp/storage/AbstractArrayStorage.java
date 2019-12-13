package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.util.Arrays;

public abstract class AbstractArrayStorage implements Storage {
    protected static final int STORAGE_LIMIT = 10_000;

    protected Resume[] storage = new Resume[STORAGE_LIMIT];
    protected int size;

    public void clear() {
        Arrays.fill(storage, 0, size, null);
        size = 0;
    }

    public void update(Resume resume) {
        int upIndex = getIndex(resume.getUuid());
        if (upIndex == -1) {
            System.out.println("Resume not found ");
            return;
        }
        storage[upIndex] = resume;
    }

    public Resume get(String uuid) {
        int gIndex = getIndex(uuid);
        if (gIndex < 0) {
            System.out.println("Resume not found");
            return null;
        }
        return storage[gIndex];
    }

    public Resume[] getAll() {
        return Arrays.copyOf(storage, size);
    }

    public int size() {
        return size;
    }

    protected abstract int getIndex(String uuid);
}