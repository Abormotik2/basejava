package com.urise.webapp.storage;

import com.urise.webapp.exception.ExistStorageException;
import com.urise.webapp.exception.NotExistStorageException;
import com.urise.webapp.exception.StorageException;
import com.urise.webapp.model.Resume;

import java.util.Arrays;

public abstract class AbstractArrayStorage extends AbstractStorage {
    protected static final int STORAGE_LIMIT = 10_000;

    protected Resume[] storage = new Resume[STORAGE_LIMIT];
    protected int size;

    protected abstract void insert(Resume resume, Object index);

    protected abstract void remove(Object index);

    protected Object existResume(String uuid) {
        Object getIndex = getSearchKey(uuid);
        if ((Integer) getIndex >= 0) {
            throw new ExistStorageException(uuid);
        }
        return getIndex;
    }

    protected Object notExistResume(String uuid) {
        Object getIndex = getSearchKey(uuid);
        if (!isValid(getIndex)) {
            throw new NotExistStorageException(uuid);
        }
        return getSearchKey(uuid);
    }

    protected boolean isValid(Object index) {
        return (Integer) index >= 0;
    }

    protected void refresh(Object index, Resume resume) {
        storage[(Integer) index] = resume;
    }

    protected Resume show(Object index) {
        return storage[(Integer) index];
    }

    public void clear() {
        Arrays.fill(storage, 0, size, null);
        size = 0;
    }

    public void save(Resume resume) {
        Object index = existResume(resume.getUuid());
        if (size >= storage.length) {
            throw new StorageException("Storage overflow", resume.getUuid());
        }
        insert(resume, index);
        size++;
    }

    public void delete(String uuid) {
        Object index = notExistResume(uuid);
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