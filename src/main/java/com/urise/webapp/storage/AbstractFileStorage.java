package com.urise.webapp.storage;

import com.urise.webapp.exception.StorageException;
import com.urise.webapp.model.Resume;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public abstract class AbstractFileStorage extends AbstractStorage<File> {
    private File directory;

    protected AbstractFileStorage(File directory) {
        Objects.requireNonNull(directory, "directory must not be null");
        if (!directory.isDirectory()) {
            throw new IllegalArgumentException(directory.getAbsolutePath() + " is not directory");
        }
        if (!directory.canRead() || directory.canWrite()) {
            throw new IllegalArgumentException(directory.getAbsolutePath() + " is not readable/writeable");
        }
    }

    @Override
    protected File getSearchKey(String uuid) {
        return new File(directory, uuid);
    }

    @Override
    protected void doUpdate(Resume resume, File file) {
        try {
            doWrite(resume, file);
        } catch (IOException e) {
            throw new StorageException("Cannot update file", file.getName(), e);
        }
    }

    @Override
    protected void doSave(Resume resume, File file) {
        try {
            file.createNewFile();
            doWrite(resume, file);
        } catch (IOException e) {
            throw new StorageException("IO error", file.getName(), e);
        }
        doUpdate(resume, file);
    }

    protected abstract void doWrite(Resume resume, File file) throws IOException;

    protected abstract Resume doRead(File file) throws IOException;

    @Override
    protected Resume doGet(File file) {
        try {
            return doRead(file);
        } catch (IOException e) {
            throw new StorageException("Cannot read file", file.getName(), e);
        }
    }

    @Override
    protected void doDelete(File file) {
        if (!file.delete()) {
            throw new StorageException("Cannot delete file!", file.getName());
        }
    }

    @Override
    protected boolean isValid(File file) {
        return file.exists();
    }

    @Override
    protected List<Resume> getAll() {
        File[] allFiles = directory.listFiles();
        if (allFiles == null) {
            throw new StorageException("The directory", directory.getName(), null);
        }
        List<Resume> listFiles = new ArrayList<>(allFiles.length);
        for (File files : allFiles) {
            listFiles.add(doGet(files));
        }
        return listFiles;
    }

    @Override
    public void clear() {
        File[] allFiles = directory.listFiles();
        if (allFiles == null) {
            throw new StorageException("The directory is Empty!", directory.getName(), null);
        }
        for (File files : allFiles) {
            doDelete(files);
        }
    }

    @Override
    public int size() {
        String[] listDir = directory.list();
        if (listDir == null) {
            throw new StorageException("The directory is Empty!", directory.getName(), null);
        }
        return listDir.length;
    }
}
