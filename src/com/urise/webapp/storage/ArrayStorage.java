package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.util.Arrays;

/**
 * Array based storage for Resumes
 */
public class ArrayStorage {
    private Resume[] storage = new Resume[10000];
    private int size;

    public void clear() {
        Arrays.fill(storage, null);
    }

    public void update(Resume r) {
        Integer update = pass(r.getUuid());
        if (update == null) {
            System.out.println("Error!!! Resume not found ");
            return;
        }
        storage[update] = r;
    }

    public void save(Resume r) {
        Integer same = pass(r.getUuid());
        if (size >= 10000) {
            System.out.println("The array is full!!!");
            return;
        }
        if(same != null){
            System.out.println("This resume is already save!");
            return;
        }
        storage[size] = r;
        size++;
    }

    public Resume get(String uuid) {
        Integer get = pass(uuid);
        if (get == null) {
            System.out.println("Resume uuid not found");
            return null;
        }
        return storage[get];
    }

    public void delete(String uuid) {
        Integer index = pass(uuid);
        if (index == null) {
            System.out.println("Resume not found");
            return;
        }
        if (index == size) {
            storage[index] = null;
            size--;
            return;
        }
        while (index < size) {
            storage[index] = storage[index + 1];
            index++;
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

    private Integer pass(String uuid) {
        for (int i = 0; i < size; i++) {
            if (storage[i].getUuid().equals(uuid)) {
                return i;
            }
        }
        return null;
    }
}