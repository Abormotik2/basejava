package com.urise.webapp.storage;

import com.urise.webapp.exception.StorageException;
import com.urise.webapp.model.Resume;

import java.util.Arrays;

public abstract class AbstractArrayStorage extends AbstractStorage {
    protected static final int STORAGE_LIMIT = 10_000;

    protected Resume[] storage = new Resume[STORAGE_LIMIT];
    protected int size;

    protected abstract void saveResume(Resume resume, int sIndex);

    protected abstract void deleteResume(int dIndex);

    protected abstract Integer getIndex(String uuid);

    protected abstract Integer existResume(String uuid);

    protected abstract Integer notExistResume(String uuid);

    public void clear() {
        Arrays.fill(storage, 0, size, null);
        size = 0;
    }

    public void update(Resume resume) {
        int upIndex = notExistResume(resume.getUuid());
        storage[upIndex] = resume;
    }

    public void save(Resume resume) {
        int sIndex = existResume(resume.getUuid());
        if (size >= storage.length) {
            throw new StorageException("Storage overflow" ,resume.getUuid());
        }
        saveResume(resume, sIndex);
        size++;
    }

    public Resume get(String uuid) {
        int gIndex = notExistResume(uuid);
        return storage[gIndex];
    }

    public void delete(String uuid) {
        int dIndex = notExistResume(uuid);
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