package com.urise.webapp.storage;

import com.urise.webapp.exception.ExistStorageException;
import com.urise.webapp.exception.NotExistStorageException;
import com.urise.webapp.model.Resume;

import java.util.ArrayList;
import java.util.List;

public class ListStorage extends AbstractStorage {

    private List<Resume> listResumes = new ArrayList<>();

    public void clear() {
        listResumes.clear();
    }

    public void updateResume(Object upIndex, Resume resume) {
        listResumes.set((Integer) upIndex, resume);
    }

    public void saveResume(Resume resume) {
        listResumes.add(resume);
    }

    public Resume getResume(Object gIndex, String uuid) {
        return listResumes.get((Integer) gIndex);
    }

    public void deleteResume(Object dIndex, String uuid) {
        listResumes.remove(dIndex);
    }

    public Resume[] getAll() {
        return listResumes.toArray(new Resume[0]);
    }

    public int size() {
        return listResumes.size();
    }

    protected Integer getIndex(String uuid) {
        for (int i = 0; i < listResumes.size(); i++) {
            Resume index = listResumes.get(i);
            if (index.getUuid().equals(uuid)) {
                return i;
            }
        }
        return null;
    }

    protected Integer existResume(String uuid) {
        Integer existIndex = getIndex(uuid);
        if (existIndex != null) {
            throw new ExistStorageException(uuid);
        }
        return existIndex;
    }

    protected Integer notExistResume(String uuid) {
        Integer notExistIndex = getIndex(uuid);
        if (notExistIndex == null) {
            throw new NotExistStorageException(uuid);
        }
        return notExistIndex;
    }
}