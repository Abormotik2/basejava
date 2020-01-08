package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

public abstract class AbstractStorage implements Storage {

    protected abstract Object notExistResume(String uuid);

    protected abstract Object existResume(String uuid);

    protected abstract Integer getIndex(String uuid);

    protected abstract void updateResume(Object index, Resume resume);

    protected abstract void saveResume(Resume resume, Object index);

    protected abstract Resume getResume(Object index);

    protected abstract void deleteResume(Object index);

    protected abstract boolean validIndex(String uuid);

    public void update(Resume resume) {
        Object index = notExistResume(resume.getUuid());
        updateResume(index, resume);
    }

    public void save(Resume resume) {
        Object index = existResume(resume.getUuid());
        saveResume(resume, index);
    }

    public Resume get(String uuid) {
        Object index = notExistResume(uuid);
        return getResume(index);
    }

    public void delete(String uuid) {
        Object index = notExistResume(uuid);
        deleteResume(index);
    }

}