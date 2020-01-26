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
    protected void doUpdate(Resume resume, String uuid) {
        mapResumes.put(uuid, resume);
    }

    @Override
    protected void doSave(Resume resume, String uuid) {
        mapResumes.put(resume.getUuid(), resume);
    }

    @Override
    protected Resume doGet(String uuid) {
        return mapResumes.get(uuid);
    }

    @Override
    protected void doDelete(String uuid) {
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