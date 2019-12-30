package com.urise.webapp.storage;

import com.urise.webapp.exception.ExistStorageException;
import com.urise.webapp.exception.NotExistStorageException;
import com.urise.webapp.model.Resume;

import java.util.HashMap;
import java.util.Map;

public class MapStorage implements Storage {

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
        existResume(resume);
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

    public void existResume(Resume resume) {
        if (mapResumes.containsKey(resume.getUuid())) {
            throw new ExistStorageException(resume.getUuid());
        }
    }

    public void notExistResume(String uuid) {
        if (!mapResumes.containsKey(uuid)) {
            throw new NotExistStorageException(uuid);
        }
    }
}