package com.urise.webapp.storage;

import com.urise.webapp.exception.ExistStorageException;
import com.urise.webapp.exception.NotExistStorageException;
import com.urise.webapp.exception.StorageException;
import com.urise.webapp.model.Resume;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public abstract class AbstractArrayStorageTest {
    private Storage storage;
    private static final String UUID_1 = "uuid1";
    private static final String UUID_2 = "uuid2";
    private static final String UUID_3 = "uuid3";
    private static final String UUID_4 = "uuid4";
    private static final Resume resume1 = new Resume(UUID_1);
    private static final Resume resume2 = new Resume(UUID_2);
    private static final Resume resume3 = new Resume(UUID_3);
    private static final Resume resume4 = new Resume(UUID_4);

    AbstractArrayStorageTest(Storage storage) {
        this.storage = storage;
    }

    @Before
    public void setUp() {
        storage.clear();
        storage.save(resume1);
        storage.save(resume2);
        storage.save(resume3);
    }

    @Test
    public void clear() {
        storage.clear();
        Assert.assertEquals(0, storage.size());
    }

    @Test
    public void update() {
        Resume resume5 = new Resume(UUID_1);
        storage.update(resume5);
        Resume updatedResume = storage.get(UUID_1);
        Assert.assertSame(resume5, updatedResume);
    }

    @Test(expected = NotExistStorageException.class)
    public void updateNotExist() {
        storage.update(resume4);
        System.out.println("Resume " + UUID_4 + " not exist");
    }

    @Test
    public void save() {
        storage.save(resume4);
        Resume existResume = storage.get(UUID_4);
        Assert.assertEquals(resume4, existResume);
        Assert.assertEquals(4, storage.size());
    }

    @Test(expected = ExistStorageException.class)
    public void saveExist() {
        storage.save(resume3);
        System.out.println("Resume " + UUID_4 + " not exist");
    }

    @Test
    public void get() {
        Resume resume = storage.get(UUID_1);
        Assert.assertEquals(resume, resume1);
    }

    @Test(expected = NotExistStorageException.class)
    public void getNotExist() {
        storage.get(UUID_4);
        System.out.println("Resume " + UUID_4 + " not exist");
    }

    @Test(expected = NotExistStorageException.class)
    public void delete() {
        storage.delete(UUID_3);
        Assert.assertEquals(2, storage.size());
        storage.get(UUID_3);
    }

    @Test(expected = NotExistStorageException.class)
    public void deleteNotExist() {
            storage.delete(UUID_4);
            System.out.println("Resume " + UUID_4 + " not exist");
    }

    @Test
    public void getAll() {
        Resume[] array = new Resume[]{resume1, resume2, resume3};
        Resume[] getAll = storage.getAll();
        Assert.assertArrayEquals(getAll, array);
        Assert.assertEquals(storage.size(), getAll.length);
    }

    @Test
    public void size() {
        Assert.assertEquals(3, storage.size());
    }

    @Test(expected = StorageException.class)
    public void saveOverflow() {
        storage.clear();
        try {
            for (int i = 0; i < AbstractArrayStorage.STORAGE_LIMIT; i++) {
                storage.save(new Resume());
            }
        } catch (ArrayStoreException ex) {
            Assert.fail("Exception not thrown");
        }
        storage.save(new Resume());
    }
}
