package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class AbstractStorage implements Storage {

    protected List<Resume> listResumes = new ArrayList<>();
    protected Map<String, Resume> mapResumes = new HashMap<>();

    protected abstract void clearCollection();

    protected abstract Integer notExistResume(String uuid);

    protected abstract void existResume(Resume resume);

    protected abstract void updateResume(Resume resume);

    protected abstract void saveResume(Resume resume);

    protected abstract Resume getResume(String uuid);

    protected abstract void deleteResume(String uuid);

    protected abstract Resume[] getAllResumes();

    protected abstract int sizeCollection();

    protected abstract Integer getIndex(String uuid);

    public void clear() {
        clearCollection();
    }

    public void update(Resume resume) {
        updateResume(resume);
    }

    public void save(Resume resume) {
        saveResume(resume);
    }

    public Resume get(String uuid) {
        return getResume(uuid);
    }

    public void delete(String uuid) {
        deleteResume(uuid);
    }

    public Resume[] getAll() {
        return getAllResumes();
    }

    public int size() {
        return sizeCollection();
    }
}