package com.urise.webapp.storage;

import com.urise.webapp.exception.ExistStorageException;
import com.urise.webapp.exception.NotExistStorageException;
import com.urise.webapp.model.Resume;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MapStorage extends AbstractStorage {

    private Map<String, Resume> mapResumes = new HashMap<>();

    @Override
    public void clear() {
        mapResumes.clear();
    }

    @Override
    public void updateResume(Object index, Resume resume) {
        mapResumes.put((String) index, resume);
    }

    @Override
    public void saveResume(Resume resume, Object index) {
        mapResumes.put(resume.getUuid(), resume);
    }

    @Override
    public Resume getResume(Object index) {
        return mapResumes.get((String)index);
    }

    @Override
    public void deleteResume(Object index) {
        mapResumes.remove((String)index);
    }

    @Override
    public Resume[] getAll() {
        return mapResumes.values().toArray(new Resume[0]);
    }

    @Override
    public int size() {
        return mapResumes.size();
    }

    @Override
    public Integer getIndex(String uuid) {
        if (mapResumes.containsKey(uuid)) {
            return new ArrayList<>(mapResumes.entrySet()).indexOf(uuid);
        }
        return null;
    }

    @Override
    protected String existResume(String uuid) {
        if (validIndex(uuid)) {
            throw new ExistStorageException(uuid);
        }
        return uuid;
    }

    @Override
    protected String notExistResume(String uuid) {
        if (!validIndex(uuid)) {
            throw new NotExistStorageException(uuid);
        }
        return uuid;
    }

    @Override
    protected boolean validIndex(String uuid) {
        return getIndex(uuid) != null;
    }
}