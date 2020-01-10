package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.util.ArrayList;
import java.util.List;

public class ListStorage extends AbstractStorage {

    private List<Resume> listResumes = new ArrayList<>();

    public void clear() {
        listResumes.clear();
    }

    protected void refresh(Object index, Resume resume) {
        listResumes.set((Integer) index, resume);
    }

    protected void insert(Resume resume, Object index) {
        listResumes.add(resume);
    }

    protected Resume show(Object index) {
        return listResumes.get((Integer) index);
    }

    protected void remove(Object index) {
        listResumes.remove(((Integer) index).intValue());
    }

    public Resume[] getAll() {
        return listResumes.toArray(new Resume[0]);
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
    protected boolean isValid(Object searchKey) {
        return searchKey != null;
    }
}