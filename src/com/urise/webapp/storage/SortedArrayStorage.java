package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.util.Arrays;

public class SortedArrayStorage extends AbstractArrayStorage {

    @Override
    protected void saveResume(Resume resume, int sIndex) {
        sIndex = sIndex * -1 - 1;
        for (int i = size - 1; i >= sIndex; i--) {
            storage[i + 1] = storage[i];
        }
        storage[sIndex] = resume;
        size++;
    }

    @Override
    protected void deleteResume(int dIndex) {
        for (int i = dIndex; i + 1 < size; i++) {
            storage[i] = storage[i + 1];
        }
        storage[size - 1] = null;
        size--;
    }

    @Override
    protected int getIndex(String uuid) {
        Resume searchKey = new Resume();
        searchKey.setUuid(uuid);
        return Arrays.binarySearch(storage, 0, size, searchKey);
    }
}