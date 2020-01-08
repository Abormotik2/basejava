package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

/**
 * Array based storage for Resumes
 */
public class ArrayStorage extends AbstractArrayStorage {

    protected void insert(Resume resume, Object index) {
        storage[size] = resume;
    }

    protected void remove(Object index) {
        storage[(int)index] = storage[size - 1];
    }

    protected Integer getIndex(String uuid) {
        for (int i = 0; i < size; i++) {
            if (storage[i].getUuid().equals(uuid)) {
                return i;
            }
        }
        return -1;
    }
}