package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.util.Arrays;

public class SortedArrayStorage extends AbstractArrayStorage {

    @Override
    protected void saveResume(Resume resume, int sIndex) {
        int varIndex = sIndex * -1 - 1;
        if (size - varIndex >= 0) System.arraycopy(storage, varIndex, storage, varIndex + 1, size - varIndex);
        storage[varIndex] = resume;
    }

    @Override
    protected void deleteResume(int dIndex) {
        System.arraycopy(storage, dIndex + 1, storage, dIndex, size - dIndex - 1);
    }

    @Override
    protected int getIndex(String uuid) {
        Resume searchKey = new Resume();
        searchKey.setUuid(uuid);
        return Arrays.binarySearch(storage, 0, size, searchKey);
    }
}