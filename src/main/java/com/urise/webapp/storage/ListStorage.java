package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.util.ArrayList;
import java.util.List;

public class ListStorage extends AbstractStorage<Integer> {

    private List<Resume> listResumes = new ArrayList<>();

    public void clear() {
        listResumes.clear();
    }

    protected void doUpdate(Resume resume, Integer searchKey) {
        listResumes.set(searchKey, resume);
    }

    protected void doSave(Resume resume, Integer searchKey) {

        listResumes.add(resume);
    }

    protected Resume doGet(Integer searchKey) {
        return listResumes.get(searchKey);
    }

    protected void doDelete(Integer searchKey) {
        listResumes.remove(searchKey.intValue());
    }

    protected List<Resume> getAll() {
        return new ArrayList<>(listResumes);
    }

    public int size() {
        return listResumes.size();
    }

    @Override
    protected Integer getSearchKey(String uuid) {
        for (int i = 0; i < listResumes.size(); i++) {
            Resume index = listResumes.get(i);
            if (index.getUuid().equals(uuid)) {
                return i;
            }
        }
        return null;
    }

    @Override
    protected boolean isValid(Integer searchKey) {
        return searchKey != null;
    }
}