package com.urise.webapp.storage;

import com.urise.webapp.exception.ExistStorageException;
import com.urise.webapp.exception.NotExistStorageException;
import com.urise.webapp.model.Resume;

public class ListStorage extends AbstractStorage {

    protected void clearCollection() {
        listResumes.clear();
    }

    protected void updateResume(Resume resume) {
        Integer upIndex = notExistResume(resume.getUuid());
        listResumes.set(upIndex, resume);
    }

    protected void saveResume(Resume resume) {
        existResume(resume);
        listResumes.add(resume);
    }

    protected Resume getResume(String uuid) {
        Integer gIndex = notExistResume(uuid);
        return listResumes.get(gIndex);
    }

    protected void deleteResume(String uuid) {
        Integer dIndex = notExistResume(uuid);
        listResumes.remove(dIndex.intValue());
    }

    protected Resume[] getAllResumes() {
        return listResumes.toArray(new Resume[0]);
    }

    protected int sizeCollection() {
        return listResumes.size();
    }

    protected Integer getIndex(String uuid) {
        for (int i = 0; i < listResumes.size(); i++) {
            Resume index = listResumes.get(i);
            if (index.getUuid().equals(uuid)) {
                return i;
            }
        }
        return null;
    }

    protected void existResume(Resume resume) {
        Integer existIndex = getIndex(resume.getUuid());
        if (existIndex != null) {
            throw new ExistStorageException(resume.getUuid());
        }
    }

    protected Integer notExistResume(String uuid) {
        Integer notExistIndex = getIndex(uuid);
        if (notExistIndex == null) {
            throw new NotExistStorageException(uuid);
        }
        return notExistIndex;
    }
}