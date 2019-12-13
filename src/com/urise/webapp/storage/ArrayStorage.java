package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

/**
 * Array based storage for Resumes
 */
public class ArrayStorage extends AbstractArrayStorage {

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

    public void delete(String uuid) {
        int dIndex = getIndex(uuid);
        if (dIndex == -1) {
            System.out.println("Resume not found");
            return;
        }
        storage[dIndex] = storage[size - 1];
        storage[size - 1] = null;
        size--;
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