package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.util.*;

public class MapResumeStorage extends AbstractStorage<Resume> {

    private Map<String, Resume> mapResumes = new HashMap<>();

    @Override
    public void clear() {
        mapResumes.clear();
    }

    @Override
    protected void refresh(Resume resume, Resume res) {
        mapResumes.put(resume.getUuid(), resume);
    }

    @Override
    protected void insert(Resume resume, Resume res) {
        mapResumes.put(resume.getUuid(), resume);
    }

    @Override
    protected Resume show(Resume resume) {
        return resume;
    }

    @Override
    protected void remove(Resume res) {
        mapResumes.remove(res.getUuid());
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
    protected Resume getSearchKey(String uuid) {
        return mapResumes.get(uuid);
    }

    @Override
    protected boolean isValid(Resume res) {
        return res != null;
    }
}