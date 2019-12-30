package com.urise.webapp.storage;

import com.urise.webapp.exception.ExistStorageException;
import com.urise.webapp.exception.NotExistStorageException;
import com.urise.webapp.model.Resume;

import java.util.ArrayList;
import java.util.List;

public class ListStorage implements Storage {

    private List<Resume> listResumes = new ArrayList<>();

    public void clear() {
        listResumes.clear();
    }

    public void update(Resume resume) {
        Integer upIndex = notExistResume(resume.getUuid());
        listResumes.set(upIndex, resume);
    }

    public void save(Resume resume) {
        existResume(resume);
        listResumes.add(resume);
    }

    public Resume get(String uuid) {
        Integer gIndex = notExistResume(uuid);
        return listResumes.get(gIndex);
    }

    public void delete(String uuid) {
        Integer dIndex = notExistResume(uuid);
        listResumes.remove(dIndex.intValue());
    }

    public Resume[] getAll() {
        return listResumes.toArray(new Resume[0]);
    }

    public int size() {
        return listResumes.size();
    }

    public Integer getIndex(String uuid) {
        for (int i = 0; i < listResumes.size(); i++) {
            Resume index = listResumes.get(i);
            if (index.getUuid().equals(uuid)) {
                return i;
            }
        }
        return null;
    }

    public void existResume(Resume resume) {
        Integer existIndex = getIndex(resume.getUuid());
        if (existIndex != null) {
            throw new ExistStorageException(resume.getUuid());
        }
    }

    public Integer notExistResume(String uuid) {
        Integer notExistIndex = getIndex(uuid);
        if (notExistIndex == null) {
            throw new NotExistStorageException(uuid);
        }
        return notExistIndex;
    }
}