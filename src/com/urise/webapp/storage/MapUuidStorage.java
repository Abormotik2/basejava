package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.util.*;

public class MapUuidStorage extends AbstractStorage {

    private static final Comparator<Resume> RESUME_COMPARATOR = Comparator.comparing(Resume::getUuid);
    private Map<String, Resume> mapResumes = new HashMap<>();

    @Override
    public void clear() {
        mapResumes.clear();
    }

    @Override
    protected void refresh(Resume resume, Object uuid) {
        mapResumes.put((String) uuid, resume);
    }

    @Override
    protected void insert(Resume resume, Object uuid) {
        mapResumes.put(resume.getUuid(), resume);
    }

    @Override
    protected Resume show(Object uuid) {
        return mapResumes.get((String) uuid);
    }

    @Override
    protected void remove(Object uuid) {
        mapResumes.remove((String) uuid);
    }

    @Override
    public List<Resume> getAllSorted() {
        List<Resume> tempList = new ArrayList<>(mapResumes.values());
        tempList.sort(RESUME_COMPARATOR);
        return tempList;
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