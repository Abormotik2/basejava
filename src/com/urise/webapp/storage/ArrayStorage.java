package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

/**
 * Array based storage for Resumes
 */
public class ArrayStorage extends AbstractArrayStorage {

    public void update(Resume resume) {
        int upIndex = getIndex(resume.getUuid());
        if (upIndex == -1) {
            System.out.println("Resume not found ");
            return;
        }
        storage[upIndex] = resume;
    }

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
        storage[size] = resume;
        size++;
    }

    protected int getIndex(String uuid) {
        for (int i = 0; i < size; i++) {
            if (storage[i].getUuid().equals(uuid)) {
                return i;
            }
        }
        return -1;
    }
}