package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.util.*;

public class MapUuidStorage extends AbstractStorage<String> {

    private Map<String, Resume> mapResumes = new HashMap<>();

    @Override
    public void clear() {
        mapResumes.clear();
    }

    @Override
    protected void refresh(Resume resume, String uuid) {
        mapResumes.put(uuid, resume);
    }

    @Override
    protected void insert(Resume resume, String uuid) {
        mapResumes.put(resume.getUuid(), resume);
    }

    @Override
    protected Resume show(String uuid) {
        return mapResumes.get(uuid);
    }

    @Override
    protected void remove(String uuid) {
        mapResumes.remove(uuid);
    }

    @Override
    protected List<Resume> getAll() {
        return new ArrayList<>(mapResumes.values());
    }

    @Override
    public int size() {
        return mapResumes.size();
    }

    @Override
    protected String getSearchKey(String uuid) {
        return uuid;
    }

    @Override
    protected boolean isValid(String searchKey) {
        return mapResumes.containsKey(searchKey);
    }
}