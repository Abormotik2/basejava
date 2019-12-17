package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.util.Arrays;

public abstract class AbstractArrayStorage implements Storage {
    protected static final int STORAGE_LIMIT = 10_000;

    protected Resume[] storage = new Resume[STORAGE_LIMIT];
    protected int size;

    protected abstract void saveResume(Resume resume, int sIndex);

    protected abstract void deleteResume(int dIndex);

    protected abstract int getIndex(String uuid);

    public void clear() {
        Arrays.fill(storage, 0, size, null);
        size = 0;
    }

    public void update(Resume resume) {
        int upIndex = getIndex(resume.getUuid());
        if (upIndex < 0) {
            System.out.println("Resume not found ");
            return;
        }
        storage[upIndex] = resume;
    }

    public void save(Resume resume) {
        int sIndex = getIndex(resume.getUuid());
        if (size >= storage.length) {
            System.out.println("The array is full!!!");
            return;
        }
        if (sIndex >= 0) {
            System.out.println("This resume is already save!");
            return;
        }
        saveResume(resume, sIndex);
        size++;
    }

    public Resume get(String uuid) {
        int gIndex = getIndex(uuid);
        if (gIndex < 0) {
            System.out.println("Resume not found");
            return null;
        }
        return storage[gIndex];
    }

    public void delete(String uuid) {
        int dIndex = getIndex(uuid);
        if (dIndex < 0) {
            System.out.println("Resume not found");
            return;
        }
        deleteResume(dIndex);
        storage[size - 1] = null;
        size--;
    }

    public Resume[] getAll() {
        return Arrays.copyOf(storage, size);
    }

    public int size() {
        return size;
    }
}