package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

public abstract class AbstractStorage implements Storage {

    protected abstract Object notExistResume(String uuid);

    protected abstract Object existResume(String uuid);

    protected abstract Integer getIndex(String uuid);

    protected abstract void updateResume(Object upIndex, Resume resume);

    protected abstract void saveResume(Resume resume);

    protected abstract Resume getResume(Object gIndex, String uuid);

    protected abstract void deleteResume(Object dIndex, String uuid);

    public void update(Resume resume) {
        Object upIndex = notExistResume(resume.getUuid());
        updateResume(upIndex, resume);
    }

    public void save(Resume resume) {
        existResume(resume.getUuid());
        saveResume(resume);
    }

    public Resume get(String uuid) {
        Object gIndex = notExistResume(uuid);
        return getResume(gIndex, uuid);
    }

    public void delete(String uuid) {
        Object dIndex = notExistResume(uuid);
        deleteResume(dIndex, uuid);
    }
}