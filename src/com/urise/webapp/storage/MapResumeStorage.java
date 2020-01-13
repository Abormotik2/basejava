package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.util.*;

public class MapResumeStorage extends AbstractStorage {

    private Map<String, Resume> mapResumes = new HashMap<>();

    @Override
    public void clear() {
        mapResumes.clear();
    }

    @Override
    protected void refresh(Resume resume, Object res) {
        mapResumes.put(resume.getUuid(), (Resume)res);
    }

    @Override
    protected void insert(Resume resume, Object res) {
        mapResumes.put(resume.getUuid(), (Resume)res);
    }

    @Override
    protected Resume show(Object res) {
        return mapResumes.get((String) res);
    }

    @Override
    protected void remove(Object res) {
        mapResumes.remove((String) res);
    }

    @Override
    public List<Resume> getAllSorted() {
        return new ArrayList<>(mapResumes.values());
    }

    @Override
    public int size() {
        return mapResumes.size();
    }

    @Override
    protected Resume getSearchKey(String uuid) {
        return mapResumes.get(uuid);
    }

    @Override
    protected boolean isValid(Object resumes) {
        return resumes != null;
    }
}