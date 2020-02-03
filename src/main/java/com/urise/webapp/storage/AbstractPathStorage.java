package com.urise.webapp.storage;

import com.urise.webapp.exception.StorageException;
import com.urise.webapp.model.Resume;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;


public abstract class AbstractPathStorage extends AbstractStorage<Path> {
    private Path directory;

    protected abstract void doWrite(Resume resume, OutputStream os) throws IOException;

    protected abstract Resume doRead(InputStream is) throws IOException;

    protected AbstractPathStorage(String dir) {
        directory = Paths.get(dir);
        Objects.requireNonNull(directory, "directory must not be null");
        if (!Files.isDirectory(directory) || !Files.isWritable(directory)) {
            throw new IllegalArgumentException(dir + " is not readable/writeable");
        }
    }

    @Override
    protected Path getSearchKey(String uuid) {
        return directory.resolve(uuid);
    }

    @Override
    protected void doUpdate(Resume resume, Path path) {
        try (OutputStream outputStream = path.toFile().toURI().toURL().openConnection().getOutputStream()) {
            doWrite(resume, outputStream);
        } catch (IOException e) {
            throw new StorageException("Cannot update path", null, e);
        }
    }

    @Override
    protected void doSave(Resume resume, Path path) {
        try {
            Files.createFile(path);
        } catch (IOException e) {
            throw new StorageException("IO error", null);
        }
        doUpdate(resume, path);
    }

    @Override
    protected Resume doGet(Path path) {
        try (InputStream inputStream = path.toFile().toURI().toURL().openConnection().getInputStream()) {
            return doRead(inputStream);
        } catch (IOException e) {
            throw new StorageException("Cannot read path", null);
        }
    }

    @Override
    protected void doDelete(Path path) {
        try {
            Files.delete(path);
        } catch (IOException e) {
            throw new StorageException("Cannot delete path!", null);
        }
    }

    @Override
    protected boolean isValid(Path path) {
        return Files.exists(path);
    }

    @Override
    protected List<Resume> getAll() {
        try {
            return Files.walk(directory).filter(Files::isRegularFile)
                    .map(this::doGet)
                    .collect(Collectors.toList());

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void clear() {
        try {
            Files.list(directory).forEach(this::doDelete);
        } catch (IOException e) {
            throw new StorageException("Path delete error", null);
        }
    }

    @Override
    public int size() {
        return directory.getNameCount();
    }
}