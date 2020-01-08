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

    public void updateResume(Object index, Resume resume) {
        listResumes.set((Integer) index, resume);
    }

    public void saveResume(Resume resume, Object index) {
        listResumes.add(resume);
    }

    public Resume getResume(Object index) {
        return listResumes.get((Integer) index);
    }

    public void deleteResume(Object index) {
        listResumes.remove((int)index);
    }

    public Resume[] getAll() {
        return listResumes.toArray(new Resume[0]);
    }

    public int size() {
        return listResumes.size();
    }

    @Override
    protected Integer getIndex(String uuid) {
        for (int i = 0; i < listResumes.size(); i++) {
            Resume index = listResumes.get(i);
            if (index.getUuid().equals(uuid)) {
                return i;
            }
        }
        return null;
    }

    @Override
    protected Integer existResume(String uuid) {
        Integer index = getIndex(uuid);
        if (validIndex(uuid)) {
            throw new ExistStorageException(uuid);
        }
        return index;
    }

    @Override
    protected Integer notExistResume(String uuid) {
        Integer index = getIndex(uuid);
        if (!validIndex(uuid)) {
            throw new NotExistStorageException(uuid);
        }
        return index;
    }

    @Override
    protected boolean validIndex(String uuid) {
        Integer index = getIndex(uuid);
        return index != null;
    }
}