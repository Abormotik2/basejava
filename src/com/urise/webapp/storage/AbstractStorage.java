package com.urise.webapp.storage;

import com.urise.webapp.exception.ExistStorageException;
import com.urise.webapp.exception.NotExistStorageException;
import com.urise.webapp.model.Resume;

import java.util.Comparator;
import java.util.List;
import java.util.logging.Logger;

public abstract class AbstractStorage<SK> implements Storage {

    private static final Logger LOG = Logger.getLogger(AbstractStorage.class.getName());

    private static final Comparator<Resume> RESUME_COMPARATOR = Comparator.comparing(Resume::getFullName).thenComparing(Resume::getUuid);

    protected abstract SK getSearchKey(String uuid);

    protected abstract void refresh(Resume resume, SK searchKey);

    protected abstract void insert(Resume resume, SK searchKey);

    protected abstract Resume show(SK searchKey);

    protected abstract void remove(SK searchKey);

    protected abstract boolean isValid(SK searchKey);

    protected abstract List<Resume> getAll();

    public void update(Resume resume) {
        LOG.info("Update " + resume);
        SK searchKey = notExistResume(resume.getUuid());
        refresh(resume, searchKey);
    }

    public void save(Resume resume) {
        LOG.info("Save " + resume);
        SK searchKey = existResume(resume.getUuid());
        insert(resume, searchKey);
    }

    public Resume get(String uuid) {
        LOG.info("Get " + uuid);
        SK searchKey = notExistResume(uuid);
        return show(searchKey);
    }

    public void delete(String uuid) {
        LOG.info("Delete  " + uuid);
        SK searchKey = notExistResume(uuid);
        remove(searchKey);
    }

    public List<Resume> getAllSorted() {
        LOG.info("GetAllSorted ");
        List<Resume> list = getAll();
        list.sort(RESUME_COMPARATOR);
        return list;
    }

    private SK existResume(String uuid) {
        SK key = getSearchKey(uuid);
        if (isValid(key)) {
            LOG.warning("Resume " + uuid + " already exist" + uuid);
            throw new ExistStorageException(uuid);
        }
        return key;
    }

    private SK notExistResume(String uuid) {
        SK key = getSearchKey(uuid);
        if (!isValid(key)) {
            LOG.warning("Resume " + uuid + " not exist" + uuid);
            throw new NotExistStorageException(uuid);
        }
        return key;
    }
}