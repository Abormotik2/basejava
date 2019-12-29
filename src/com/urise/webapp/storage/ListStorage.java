package com.urise.webapp.storage;

import com.urise.webapp.exception.ExistStorageException;
import com.urise.webapp.exception.NotExistStorageException;
import com.urise.webapp.model.Resume;

import java.util.ArrayList;
import java.util.List;

public class ListStorage extends AbstractStorage {
    private List<Resume> resumes = new ArrayList<>();

    @Override
    public void clear() {
        resumes.clear();
    }

    @Override
    public void update(Resume resume) {
        int upIndex = getIndex(resume.getUuid());
        if (upIndex == -1) {
            throw new NotExistStorageException(resume.getUuid());
        } else {
            resumes.set(upIndex, resume);
        }
    }

    @Override
    public void save(Resume resume) {
        int saveIndex = getIndex(resume.getUuid());
        if (saveIndex != -1) {
            throw new ExistStorageException(resume.getUuid());
        } else {
            resumes.add(resume);
        }
    }

    @Override
    public Resume get(String uuid) {
        int gIndex = getIndex(uuid);
        if (gIndex == -1) {
            throw new NotExistStorageException(uuid);
        }
        return resumes.get(gIndex);
    }

    @Override
    public void delete(String uuid) {
        int dIndex = getIndex(uuid);
        if (dIndex == -1) {
            throw new NotExistStorageException(uuid);
        } else {
            resumes.remove(dIndex);
        }
    }

    @Override
    public Resume[] getAll() {
        return resumes.toArray(new Resume[0]);
    }

    @Override
    public int size() {
        return resumes.size();
    }

    protected int getIndex(String uuid) {
        for (int i = 0; i < resumes.size(); i++) {
            Resume index = resumes.get(i);
            if (index.getUuid().equals(uuid)) {
                return i;
            }
        }
        return -1;
    }
}
