package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.util.Arrays;

public class SortedArrayStorage extends AbstractArrayStorage {

    @Override
    public void clear() {

    }

    @Override
    public void update(Resume resume) {
        int upIndex = getIndex(resume.getUuid());
        if (upIndex == -1) {
            System.out.println("Resume not found ");
            return;
        }
        storage[upIndex] = resume;
    }
    @Override
    public void save(Resume resume) {
        int sIndex = getIndex(resume.getUuid());
        if (size >= storage.length) {
            System.out.println("The array is full!!!");
            return;
        }
        if (sIndex != -1) {
            System.out.println("This resume is already save!");
            return;
        }
        storage[sIndex + 1] = resume;
        size++;
    }

    @Override
    public void delete(String uuid) {

    }

    @Override
    public Resume[] getAll() {
        return new Resume[0];
    }

    @Override
    public int size() {
        return 0;
    }

    @Override
    protected int getIndex(String uuid) {
        Resume searchKey = new Resume();
        searchKey.setUuid(uuid);
        return Arrays.binarySearch(storage,0,size, searchKey);
    }
}