package com.urise.webapp.storage;

import com.urise.webapp.exception.StorageException;
import com.urise.webapp.model.Resume;

import java.util.Arrays;
import java.util.List;

public abstract class AbstractArrayStorage extends AbstractStorage {
    protected static final int STORAGE_LIMIT = 10_000;

    protected Resume[] storage = new Resume[STORAGE_LIMIT];
    protected int size;

    protected abstract void doSave(Resume resume, Object index);

    protected abstract void doDelete(Object index);

    protected boolean isValid(Object index) {
        return (Integer) index >= 0;
    }

    protected void refresh(Resume resume, Object index) {
        storage[(Integer) index] = resume;
    }

    protected Resume show(Object index) {
        return storage[(Integer) index];
    }

    public void clear() {
        Arrays.fill(storage, 0, size, null);
        size = 0;
    }

   protected void insert(Resume resume, Object searchKey) {
        if (size >= storage.length) {
            throw new StorageException("Storage overflow", resume.getUuid());
        }
        doSave(resume, searchKey);
        size++;
    }

    protected void remove(Object searchKey) {
        doDelete(searchKey);
        storage[size - 1] = null;
        size--;
    }

    public List<Resume> getAllSorted() {
        return Arrays.asList(Arrays.copyOf(storage,size));
    }

    public int size() {
        return size;
    }
}