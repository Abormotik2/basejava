package com.urise.webapp.storage;

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
    public void refresh(Object uuid, Resume resume) {
        mapResumes.put((String) uuid, resume);
    }

    @Override
    public void insert(Resume resume, Object uuid) {
        mapResumes.put(resume.getUuid(), resume);
    }

    @Override
    public Resume show(Object uuid) {
        return mapResumes.get((String) uuid);
    }

    @Override
    public void remove(Object uuid) {
        mapResumes.remove((String) uuid);
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
    protected Object getSearchKey(String uuid) {
        return uuid;
    }

    @Override
    protected boolean isValid(Object searchKey) {
        return mapResumes.containsKey((String) searchKey);
    }
}