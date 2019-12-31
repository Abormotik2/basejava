package com.urise.webapp.storage;

import com.urise.webapp.exception.ExistStorageException;
import com.urise.webapp.exception.NotExistStorageException;
import com.urise.webapp.model.Resume;

/**
 * Array based storage for Resumes
 */
public class ArrayStorage extends AbstractArrayStorage {

    protected void saveResume(Resume resume, int sIndex) {
        storage[size] = resume;
    }

    protected void deleteResume(int dIndex) {
        storage[dIndex] = storage[size - 1];
    }

    protected Integer getIndex(String uuid) {
        for (int i = 0; i < size; i++) {
            if (storage[i].getUuid().equals(uuid)) {
                return i;
            }
        }
        return null;
    }

    protected Integer existResume(String uuid) {
        Integer existIndex = getIndex(uuid);
        if (existIndex != null) {
            throw new ExistStorageException(uuid);
        }
        return existIndex;
    }

    protected Integer notExistResume(String uuid) {
        Integer notExistIndex = getIndex(uuid);
        if (notExistIndex == null) {
            throw new NotExistStorageException(uuid);
        }
        return notExistIndex;
    }
}