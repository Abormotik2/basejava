package com.urise.webapp.storage;

import com.urise.webapp.exception.StorageException;
import com.urise.webapp.model.Resume;

import java.util.Arrays;

public abstract class AbstractArrayStorage extends AbstractStorage {
    protected static final int STORAGE_LIMIT = 10_000;

    protected Resume[] storage = new Resume[STORAGE_LIMIT];
    protected int size;

    protected abstract void saveResume(Resume resume, Object index);

    protected abstract void deleteResume(Object index);

    protected abstract Integer getIndex(String uuid);

    protected abstract Integer existResume(String uuid);

    protected abstract Integer notExistResume(String uuid);

    protected boolean validIndex(String uuid) {
        return getIndex(uuid) != null;
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

    public void update(Resume resume) {
        int index = notExistResume(resume.getUuid());
        updateResume(index, resume);
    }

    public void save(Resume resume) {
        int index = existResume(resume.getUuid());
        if (size >= storage.length) {
            throw new StorageException("Storage overflow", resume.getUuid());
        }
        saveResume(resume, index);
        size++;
    }

    public Resume get(String uuid) {
        int index = notExistResume(uuid);
        return getResume(index);
    }

    public void delete(String uuid) {
        int index = notExistResume(uuid);
        deleteResume(index);
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