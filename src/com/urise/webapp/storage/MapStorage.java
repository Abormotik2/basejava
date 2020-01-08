package com.urise.webapp.storage;

import com.urise.webapp.exception.ExistStorageException;
import com.urise.webapp.exception.NotExistStorageException;
import com.urise.webapp.model.Resume;

import java.util.HashMap;
import java.util.Map;

public class MapStorage extends AbstractStorage {

    private Map<String, Resume> mapResumes = new HashMap<>();

    @Override
    public void clear() {
        mapResumes.clear();
    }

    @Override
    public void updateResume(Object uuid, Resume resume) {
        mapResumes.put((String) uuid, resume);
    }

    @Override
    public void insert(Resume resume, Object uuid) {
        mapResumes.put(resume.getUuid(), resume);
    }

    @Override
    public Resume getResume(Object uuid) {
        return mapResumes.get((String)uuid);
    }

    @Override
    public void remove(Object uuid) {
        mapResumes.remove((String)uuid);
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
    protected Object getIndex(String uuid) {
        if (mapResumes.containsKey(uuid)) {
            return uuid;
        }
        return null;
    }

    @Override
    protected String existResume(String uuid) {
        Object getKey = getIndex(uuid);
        if (isValid(uuid)) {
            throw new ExistStorageException(uuid);
        }
        return (String)getKey;
    }

    @Override
    protected String notExistResume(String uuid) {
        Object getKey = getIndex(uuid);
        if (!isValid(uuid)) {
            throw new NotExistStorageException(uuid);
        }
        return (String)getKey;
    }

    @Override
    protected boolean isValid(Object uuid) {
        return getIndex((String)uuid) != null;
    }
}