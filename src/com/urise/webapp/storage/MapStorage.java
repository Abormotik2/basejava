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
    public void updateResume(Object upIndex, Resume resume) {
        mapResumes.put((String) upIndex, resume);
    }

    @Override
    public void saveResume(Resume resume) {
        mapResumes.put(resume.getUuid(), resume);
    }

    @Override
    public Resume getResume(Object gIndex) {
        return mapResumes.get(gIndex);
    }

    @Override
    public void deleteResume(Object dIndex) {
        mapResumes.remove(dIndex);
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
        return null;
    }

    @Override
    protected String existResume(String uuid) {
        if (mapResumes.containsKey(uuid)) {
            throw new ExistStorageException(uuid);
        }
        return uuid;
    }

    @Override
    protected String notExistResume(String uuid) {
        if (!mapResumes.containsKey(uuid)) {
            throw new NotExistStorageException(uuid);
        }
        return uuid;
    }
}