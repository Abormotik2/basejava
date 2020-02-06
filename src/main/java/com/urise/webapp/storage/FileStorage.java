package com.urise.webapp.storage;

import com.urise.webapp.exception.StorageException;
import com.urise.webapp.model.Resume;
import com.urise.webapp.storage.strategy.StrategySerializer;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class FileStorage extends AbstractStorage<File> {
    private File directory;

    protected StrategySerializer serialized;

    protected FileStorage(File directory, StrategySerializer serialized) {
        Objects.requireNonNull(directory, "directory must not be null");
        this.directory = directory;
        this.serialized = serialized;
        if (!directory.isDirectory()) {
            throw new IllegalArgumentException(directory.getAbsolutePath() + " is not directory");
        }
        if (!directory.canRead() || !directory.canWrite()) {
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
            serialized.doWrite(resume, new BufferedOutputStream(new FileOutputStream(file)));
        } catch (IOException e) {
            throw new StorageException("Cannot update file", file.getName(), e);
        }
    }

    @Override
    protected void doSave(Resume resume, File file) {
        try {
            file.createNewFile();
        } catch (IOException e) {
            throw new StorageException("IO error", file.getName(), e);
        }
        doUpdate(resume, file);
    }

    @Override
    protected Resume doGet(File file) {
        try {
            return serialized.doRead(new BufferedInputStream(new FileInputStream(file)));
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
        File[] allFiles = getFiles();
        List<Resume> listFiles = new ArrayList<>(allFiles.length);
        for (File files : allFiles) {
            listFiles.add(doGet(files));
        }
        return listFiles;
    }

    @Override
    public void clear() {
        File[] allFiles = getFiles();
        for (File files : allFiles) {
            doDelete(files);
        }
    }

    @Override
    public int size() {
        File[] allFiles = getFiles();
        return allFiles.length;
    }

    private File[] getFiles() {
        File[] allFiles = directory.listFiles();
        if (allFiles == null) {
            throw new StorageException("The directory is Empty!", directory.getName(), null);
        }
        return allFiles;
    }
}