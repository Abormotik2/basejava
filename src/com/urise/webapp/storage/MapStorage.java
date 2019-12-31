package com.urise.webapp.storage;

import com.urise.webapp.exception.ExistStorageException;
import com.urise.webapp.exception.NotExistStorageException;
import com.urise.webapp.model.Resume;

import java.util.HashMap;
import java.util.Map;

public class MapStorage extends AbstractStorage{

    private Map<String, Resume> mapResumes = new HashMap<>();

    @Override
    public void clear() {
        mapResumes.clear();
    }

    @Override
    public void update(Resume resume) {
        notExistResume(resume.getUuid());
        mapResumes.put(resume.getUuid(), resume);
    }

    @Override
    public void save(Resume resume) {
        existResume(resume.getUuid());
        mapResumes.put(resume.getUuid(), resume);
    }

    @Override
    public Resume get(String uuid) {
        notExistResume(uuid);
        return mapResumes.get(uuid);
    }

    @Override
    public void delete(String uuid) {
        notExistResume(uuid);
        mapResumes.remove(uuid);
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
    protected Integer existResume(String uuid) {
        if (mapResumes.containsKey(uuid)) {
            throw new ExistStorageException(uuid);
        }
        return Integer.valueOf(uuid);
    }

    @Override
    protected Integer notExistResume(String uuid) {
        if (!mapResumes.containsKey(uuid)) {
            throw new NotExistStorageException(uuid);
        }
        return Integer.valueOf(uuid);
    }
}