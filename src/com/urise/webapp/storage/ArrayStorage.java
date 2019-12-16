package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

/**
 * Array based storage for Resumes
 */
public class ArrayStorage extends AbstractArrayStorage {

    protected int getIndex(String uuid) {
        for (int i = 0; i < size; i++) {
            if (storage[i].getUuid().equals(uuid)) {
                return i;
            }
        }
        return -1;
    }

    protected void saveResume(Resume resume, int sIndex) {
        storage[size] = resume;
    }

    protected void deleteResume(int dIndex) {
        storage[dIndex] = storage[size - 1];
        storage[size - 1] = null;
    }
}