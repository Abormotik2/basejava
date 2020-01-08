package com.urise.webapp.storage;

import com.urise.webapp.exception.ExistStorageException;
import com.urise.webapp.exception.NotExistStorageException;
import com.urise.webapp.model.Resume;

/**
 * Array based storage for Resumes
 */
public class ArrayStorage extends AbstractArrayStorage {

    protected void saveResume(Resume resume, Object index) {
        storage[size] = resume;
    }

    protected void deleteResume(Object index) {
        storage[(int)index] = storage[size - 1];
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
        Integer index = getIndex(uuid);
        if (index != null) {
            throw new ExistStorageException(uuid);
        }
        return size;
    }

    protected Integer notExistResume(String uuid) {
        Integer index = getIndex(uuid);
        if (index == null) {
            throw new NotExistStorageException(uuid);
        }
        return index;
    }
}