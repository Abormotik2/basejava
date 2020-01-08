package com.urise.webapp.storage;

import com.urise.webapp.exception.StorageException;
import com.urise.webapp.model.Resume;

import java.util.Arrays;

public abstract class AbstractArrayStorage extends AbstractStorage {
    protected static final int STORAGE_LIMIT = 10_000;

    protected Resume[] storage = new Resume[STORAGE_LIMIT];
    protected int size;

    protected abstract void insert(Resume resume, Object index);

    protected abstract void remove(Object index);

    protected abstract Integer getIndex(String uuid);

    protected abstract Integer existResume(String uuid);

    protected abstract Integer notExistResume(String uuid);

    protected boolean isValid(Object uuid) {
        return getIndex((String)uuid) != null;
    }

    protected void updateResume(Object index, Resume resume) {
        storage[(int) index] = resume;
    }

    protected Resume getResume(Object index) {
        return storage[(int) index];
    }

    public void clear() {
        Arrays.fill(storage, 0, size, null);
        size = 0;
    }

    public void save(Resume resume) {
        int index = existResume(resume.getUuid());
        if (size >= storage.length) {
            throw new StorageException("Storage overflow", resume.getUuid());
        }
        insert(resume, index);
        size++;
    }

    public void delete(String uuid) {
        int index = notExistResume(uuid);
        remove(index);
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