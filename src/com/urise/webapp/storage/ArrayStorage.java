package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.util.Arrays;

/**
 * Array based storage for Resumes
 */
public class ArrayStorage {
    private Resume[] storage = new Resume[10_000];
    private int size;

    public void clear() {
        size = 0;
    }

    public void update(Resume resume) {
        int upIndex = pass(resume.getUuid());
        if (upIndex == -1) {
            System.out.println("Error!!! Resume not found ");
            return;
        }
        storage[upIndex] = resume;
    }

    public void save(Resume resume) {
        int sIndex = pass(resume.getUuid());
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

    public Resume get(String uuid) {
        int gIndex = pass(uuid);
        if (gIndex == -1) {
            System.out.println("Resume uuid not found");
            return null;
        }
        return storage[gIndex];
    }

    public void delete(String uuid) {
        int delIndex = pass(uuid);
        if (delIndex == -1) {
            System.out.println("Resume not found");
            return;
        }
        while (delIndex < size) {
            storage[delIndex] = storage[delIndex + 1];
            delIndex++;
        }
        if (delIndex == size) {
            storage[delIndex] = null;
            size--;
            return;
        }
        size--;
    }

    /**
     * @return array, contains only Resumes in storage (without null)
     */
    public Resume[] getAll() {
        return Arrays.copyOf(storage, size());
    }

    public int size() {
        return size;
    }

    private int pass(String uuid) {
        for (int i = 0; i < size; i++) {
            if (storage[i].getUuid().equals(uuid)) {
                return i;
            }
        }
        return -1;
    }
}