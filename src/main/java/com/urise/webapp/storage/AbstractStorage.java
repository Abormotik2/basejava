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

    protected abstract void doUpdate(Resume resume, SK searchKey);

    protected abstract void doSave(Resume resume, SK searchKey);

    protected abstract Resume doGet(SK searchKey);

    protected abstract void doDelete(SK searchKey);

    protected abstract boolean isValid(SK searchKey);

    protected abstract List<Resume> getAll();

    public void update(Resume resume) {
        LOG.info("Update " + resume);
        SK searchKey = notExistResume(resume.getUuid());
        doUpdate(resume, searchKey);
    }

    public void save(Resume resume) {
        LOG.info("Save " + resume);
        SK searchKey = existResume(resume.getUuid());
        doSave(resume, searchKey);
    }

    public Resume get(String uuid) {
        LOG.info("Get " + uuid);
        SK searchKey = notExistResume(uuid);
        return doGet(searchKey);
    }

    public void delete(String uuid) {
        LOG.info("Delete  " + uuid);
        SK searchKey = notExistResume(uuid);
        doDelete(searchKey);
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