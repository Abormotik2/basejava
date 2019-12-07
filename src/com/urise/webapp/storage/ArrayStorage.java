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
        Arrays.fill(storage, null);
    }

    public void update(Resume resume) {
        int updateIndex = pass(resume.getUuid());
        if (updateIndex == -1) {
            System.out.println("Error!!! Resume not found ");
            return;
        }
        storage[updateIndex] = resume;
    }

    public void save(Resume resume) {
        int saveIndex = pass(resume.getUuid());
        if (size >= 10_000) {
            System.out.println("The array is full!!!");
            return;
        }
        if(saveIndex != -1){
            System.out.println("This resume is already save!");
            return;
        }
        storage[size] = resume;
        size++;
    }

    public Resume get(String uuid) {
        int getIndex = pass(uuid);
        if (getIndex == -1) {
            System.out.println("Resume uuid not found");
            return null;
        }
        return storage[getIndex];
    }

    public void delete(String uuid) {
        int delIndex = pass(uuid);
        if (delIndex == -1) {
            System.out.println("Resume not found");
            return;
        }
        if (delIndex == size) {
            storage[delIndex] = null;
            size--;
            return;
        }
        while (delIndex < size) {
            storage[delIndex] = storage[delIndex + 1];
            delIndex++;
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