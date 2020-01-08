package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

public abstract class AbstractStorage implements Storage {

    protected abstract Object notExistResume(String uuid);

    protected abstract Object existResume(String uuid);

    protected abstract Object getIndex(String uuid);

    protected abstract void updateResume(Object searchKey, Resume resume);

    protected abstract void insert(Resume resume, Object searchKey);

    protected abstract Resume getResume(Object searchKey);

    protected abstract void remove(Object searchKey);

    protected abstract boolean isValid(Object uuid);

    public void update(Resume resume) {
        Object searchKey = notExistResume(resume.getUuid());
        updateResume(searchKey, resume);
    }

    public void save(Resume resume) {
        Object searchKey = existResume(resume.getUuid());
        insert(resume, searchKey);
    }

    public Resume get(String uuid) {
        Object searchKey = notExistResume(uuid);
        return getResume(searchKey);
    }

    public void delete(String uuid) {
        Object searchKey = notExistResume(uuid);
        remove(searchKey);
    }

}