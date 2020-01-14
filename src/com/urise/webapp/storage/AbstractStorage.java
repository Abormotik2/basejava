package com.urise.webapp.storage;

import com.urise.webapp.exception.ExistStorageException;
import com.urise.webapp.exception.NotExistStorageException;
import com.urise.webapp.model.Resume;

import java.util.Collections;
import java.util.List;

public abstract class AbstractStorage implements Storage {

    protected abstract Object getSearchKey(String uuid);

    protected abstract void refresh(Resume resume, Object searchKey);

    protected abstract void insert(Resume resume, Object searchKey);

    protected abstract Resume show(Object searchKey);

    protected abstract void remove(Object searchKey);

    protected abstract boolean isValid(Object searchKey);

    protected abstract List<Resume> getAll();

    public void update(Resume resume) {
        Object searchKey = notExistResume(resume.getUuid());
        refresh(resume,searchKey);
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
        Object key = getSearchKey(uuid);
        if (isValid(key)) {
            throw new ExistStorageException(uuid);
        }
        return key;
    }

    public List<Resume> getAllSorted() {
        List<Resume> list = getAll();
        Collections.sort(list);
        return list;
    }

    private Object notExistResume(String uuid) {
        Object key = getSearchKey(uuid);
        if (!isValid(key)) {
            throw new NotExistStorageException(uuid);
        }
        return key;
    }
}