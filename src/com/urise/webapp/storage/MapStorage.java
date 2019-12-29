package com.urise.webapp.storage;

import com.urise.webapp.exception.ExistStorageException;
import com.urise.webapp.exception.NotExistStorageException;
import com.urise.webapp.model.Resume;

import java.util.HashMap;
import java.util.Map;

public class MapStorage extends AbstractStorage {

    private Map<String, Resume> resumes = new HashMap<>();

    @Override
    public void clear() {
        resumes.clear();
    }

    @Override
    public void update(Resume resume) {
        if (!resumes.containsKey(resume.getUuid())) {
            throw new NotExistStorageException(resume.getUuid());
        } else {
            resumes.put(resume.getUuid(), resume);
        }
    }

    @Override
    public void save(Resume resume) {
        if (resumes.containsKey(resume.getUuid())) {
            throw new ExistStorageException(resume.getUuid());
        } else {
            resumes.put(resume.getUuid(), resume);
        }
    }

    @Override
    public Resume get(String uuid) {
        Resume ourResume = resumes.get(uuid);
        if (ourResume == null) {
            throw new NotExistStorageException(uuid);
        } else {
            return ourResume;
        }
    }

    @Override
    public void delete(String uuid) {
       Resume delResume = resumes.remove(uuid);
       if(delResume == null){
           throw new NotExistStorageException(uuid);
       }
    }

    @Override
    public Resume[] getAll() {
        return resumes.values().toArray(new Resume[0]);
    }

    @Override
    public int size() {
        return resumes.size();
    }
}
