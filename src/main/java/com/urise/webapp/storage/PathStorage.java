package com.urise.webapp.storage;

import com.urise.webapp.exception.StorageException;
import com.urise.webapp.model.Resume;
import com.urise.webapp.storage.serializer.StreamSerializer;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;


public class PathStorage extends AbstractStorage<Path> {
    private Path directory;

    protected StreamSerializer serialized;

    protected PathStorage(String dir, StreamSerializer serialized) {
        this.serialized = serialized;
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
        try (OutputStream outputStream = new BufferedOutputStream(Files.newOutputStream(path))) {
            serialized.doWrite(resume, outputStream);
        } catch (IOException e) {
            throw new StorageException("Path update error", getFileName(path), e);
        }
    }

    @Override
    protected void doSave(Resume resume, Path path) {
        try {
            Files.createFile(path);
        } catch (IOException e) {
            throw new StorageException("Path save error", getFileName(path), e);
        }
        doUpdate(resume, path);
    }

    @Override
    protected Resume doGet(Path path) {
        try (InputStream inputStream = new BufferedInputStream(Files.newInputStream(path))) {
            return serialized.doRead(inputStream);
        } catch (IOException e) {
            throw new StorageException("Path read error", getFileName(path), e);
        }
    }

    @Override
    protected void doDelete(Path path) {
        try {
            Files.delete(path);
        } catch (IOException e) {
            throw new StorageException("Path delete error", getFileName(path), e);
        }
    }

    @Override
    protected boolean isValid(Path path) {
        return Files.isRegularFile(path);
    }

    @Override
    protected List<Resume> getAll() {
        return listFiles()
                .map(this::doGet)
                .collect(Collectors.toList());
    }

    @Override
    public void clear() {
        listFiles().forEach(this::doDelete);
    }

    @Override
    public int size() {
        return (int) listFiles().count();
    }

    private Stream<Path> listFiles() {
        try {
            return Files.list(directory);
        } catch (IOException e) {
            throw new StorageException("Path size/clear/getAll error", null);
        }
    }

    private String getFileName(Path path) {
        return path.getFileName().toString();
    }
}