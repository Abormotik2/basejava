package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.util.Arrays;

public class SortedArrayStorage extends AbstractArrayStorage {

    @Override
    protected void insert(Resume resume, Object index) {
        int varIndex = (Integer)index * -1 - 1;
        if (size - varIndex >= 0) System.arraycopy(storage, varIndex, storage, varIndex + 1, size - varIndex);
        storage[varIndex] = resume;
    }

    @Override
    protected void remove(Object index) {
        System.arraycopy(storage, (Integer)index + 1, storage, (Integer)index, size - (Integer)index - 1);
    }

    @Override
    protected Integer getSearchKey(String uuid) {
        Resume index = new Resume(uuid);
        return Arrays.binarySearch(storage, 0, size, index);
    }
}