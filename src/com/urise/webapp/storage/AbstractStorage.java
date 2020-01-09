package com.urise.webapp.storage;

import com.urise.webapp.exception.ExistStorageException;
import com.urise.webapp.exception.NotExistStorageException;
import com.urise.webapp.model.Resume;

public abstract class AbstractStorage implements Storage {

    protected abstract Object getSearchKey(String uuid);

    protected abstract void refresh(Object searchKey, Resume resume);

    protected abstract void insert(Resume resume, Object searchKey);

    protected abstract Resume show(Object searchKey);

    protected abstract void remove(Object searchKey);

    protected abstract boolean isValid(Object searchKey);

    public void update(Resume resume) {
        Object searchKey = notExistResume(resume.getUuid());
        refresh(searchKey, resume);
    }

    public void save(Resume resume) {
        Object searchKey = existResume(resume.getUuid());
        insert(resume, searchKey);
    }

    public Resume get(String uuid) {
        Object searchKey = notExistResume(uuid);
        return show(searchKey);
    }

    public void delete(String uuid) {
        Object searchKey = notExistResume(uuid);
        remove(searchKey);
    }

    private Object existResume(String uuid) {
        Object getKey = getSearchKey(uuid);
        if (isValid(getKey)) {
            throw new ExistStorageException(uuid);
        }
        return getKey;
    }

    private Object notExistResume(String uuid) {
        Object getKey = getSearchKey(uuid);
        if (!isValid(getKey)) {
            throw new NotExistStorageException(uuid);
        }
        return getKey;
    }
}