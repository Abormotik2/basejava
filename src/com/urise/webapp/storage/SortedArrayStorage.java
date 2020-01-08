package com.urise.webapp.storage;

import com.urise.webapp.exception.ExistStorageException;
import com.urise.webapp.exception.NotExistStorageException;
import com.urise.webapp.model.Resume;

import java.util.Arrays;

public class SortedArrayStorage extends AbstractArrayStorage {

    @Override
    protected void saveResume(Resume resume, Object sIndex) {
        int varIndex = (int)sIndex * -1 - 1;
        if (size - varIndex >= 0) System.arraycopy(storage, varIndex, storage, varIndex + 1, size - varIndex);
        storage[varIndex] = resume;
    }

    @Override
    protected void deleteResume(Object index) {
        System.arraycopy(storage, (int)index + 1, storage, (int)index, size - (int)index - 1);
    }

    @Override
    protected Integer getIndex(String uuid) {
        Resume searchKey = new Resume(uuid);
        return Arrays.binarySearch(storage, 0, size, searchKey);
    }

    @Override
    protected Integer existResume(String uuid) {
        Integer existIndex = getIndex(uuid);
        if (existIndex >= 0) {
            throw new ExistStorageException(uuid);
        }
        return existIndex;
    }

    @Override
    protected Integer notExistResume(String uuid) {
        Integer notExistIndex = getIndex(uuid);
        if (notExistIndex == null) {
            throw new NotExistStorageException(uuid);
        }
        return notExistIndex;
    }
}