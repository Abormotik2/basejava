package com.urise.webapp.storage;

import com.urise.webapp.exception.StorageException;
import com.urise.webapp.model.Resume;

import java.util.Arrays;
import java.util.List;

public abstract class AbstractArrayStorage extends AbstractStorage<Integer> {

    protected static final int STORAGE_LIMIT = 10_000;

    protected Resume[] storage = new Resume[STORAGE_LIMIT];

    protected int size;

    protected abstract void insert(Resume resume, Integer index);

    protected abstract void remove(Integer index);

    protected boolean isValid(Integer index) {
        return index >= 0;
    }

    protected void doUpdate(Resume resume, Integer index) {
        storage[index] = resume;
    }

    protected Resume doGet(Integer index) {
        return storage[index];
    }

    public void clear() {
        Arrays.fill(storage, 0, size, null);
        size = 0;
    }

    protected void doSave(Resume resume, Integer searchKey) {
        if (size >= storage.length) {
            throw new StorageException("Storage overflow", resume.getUuid());
        }
        doSave(resume, searchKey);
        size++;
    }

    protected void doDelete(Integer searchKey) {
        doDelete(searchKey);
        storage[size - 1] = null;
        size--;
    }

    protected List<Resume> getAll() {
        return Arrays.asList(Arrays.copyOf(storage, size));
    }

    public int size() {
        return size;
    }
}