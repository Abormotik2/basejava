package com.urise.webapp.storage;

import com.urise.webapp.exception.StorageException;
import com.urise.webapp.model.Resume;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public abstract class AbstractArrayStorageTest {
    private Storage storage;
    protected static final String UUID_1 = "uuid1";
    protected static final String UUID_2 = "uuid2";
    protected static final String UUID_3 = "uuid3";
    protected static final String UUID_4 = "uuid4";

    AbstractArrayStorageTest(Storage storage) {
        this.storage = storage;
    }

    @Before
    public void setUp() {
        storage.clear();
        storage.save(new Resume(UUID_1));
        storage.save(new Resume(UUID_2));
        storage.save(new Resume(UUID_3));
    }

    @Test
    public void clear() {
        storage.clear();
        Assert.assertEquals(0, storage.size());
    }

    @Test
    public void update() {
        Resume resume = storage.get(UUID_1);
        // Change resume here
        storage.update(resume);
        Resume updatedResume = storage.get(UUID_1);
        Assert.assertEquals(resume, updatedResume);
    }

    @Test
    public void save() {
        storage.save(new Resume(UUID_4));
        Resume resume = storage.get(UUID_4);
        Assert.assertNotNull(resume);
        Assert.assertEquals(4, storage.size());
    }

    @Test
    public void get() {
        Resume resume = storage.get(UUID_1);
        Assert.assertNotNull(resume);
    }

    @Test
    public void delete() {
        storage.delete(UUID_3);
        Assert.assertEquals(2, storage.size());
    }

    @Test
    public void getAll() {
        for (int i = 0; i < storage.size(); i++) {
            System.out.println(storage);
        }
    }

    @Test
    public void size() {
        Assert.assertEquals(3, storage.size());
    }

    @Test(expected = StorageException.class)
    public void overflow() {
        for (int i = 3; i < 10000; i++) {
            storage.save(new Resume());
        }
        Assert.assertEquals(10000, storage.size());
        storage.save(new Resume());
        }
    }