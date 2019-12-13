package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.util.Arrays;

public class SortedArrayStorage extends AbstractArrayStorage {

    @Override
    public void save(Resume resume) {
        int sIndex = getIndex(resume.getUuid());
        if (size >= storage.length) {
            System.out.println("The array is full!!!");
            return;
        }
        if (sIndex >= 0) {
            System.out.println("This resume is already save!");
            return;
        }
        storage[size] = resume;
        size++;
    }

    @Override
    public void delete(String uuid) {
        int dIndex = getIndex(uuid);
        if (dIndex < 0) {
            System.out.println("Resume not found");
            return;
        }
        storage[dIndex] = storage[size - 1];
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