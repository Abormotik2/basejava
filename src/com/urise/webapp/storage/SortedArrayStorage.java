package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.util.Arrays;

public class SortedArrayStorage extends AbstractArrayStorage {

    @Override
    protected void insert(Resume resume, Object index) {
        int varIndex = (int)index * -1 - 1;
        if (size - varIndex >= 0) System.arraycopy(storage, varIndex, storage, varIndex + 1, size - varIndex);
        storage[varIndex] = resume;
    }

    @Override
    protected void remove(Object index) {
        System.arraycopy(storage, (int)index + 1, storage, (int)index, size - (int)index - 1);
    }

    @Override
    protected Integer getIndex(String uuid) {
        Resume index = new Resume(uuid);
        return Arrays.binarySearch(storage, 0, size, index);
    }
}