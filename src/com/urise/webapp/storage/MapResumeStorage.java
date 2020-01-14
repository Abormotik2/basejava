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
        mapResumes.put(resume.getUuid(), resume);
    }

    @Override
    protected void insert(Resume resume, Object res) {
        mapResumes.put(resume.getUuid(), resume);
    }

    @Override
    protected Resume show(Object resume) {
        return (Resume) resume;
    }

    @Override
    protected void remove(Object res) {
        mapResumes.remove(((Resume)res).getUuid());
    }

    @Override
    public List<Resume> getAll() {
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
    protected boolean isValid(Object res) {
        return res != null;
    }
}