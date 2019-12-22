package com.urise.webapp.storage;

import com.urise.webapp.exception.NotExistStorageException;
import com.urise.webapp.exception.StorageException;
import com.urise.webapp.model.Resume;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;

public abstract class AbstractArrayStorageTest {
    private Storage storage;
    private static final String UUID_1 = "uuid1";
    private static final String UUID_2 = "uuid2";
    private static final String UUID_3 = "uuid3";
    private static final String UUID_4 = "uuid4";
    private Resume resume1 = new Resume(UUID_1);
    private Resume resume2 = new Resume(UUID_2);
    private Resume resume3 = new Resume(UUID_3);
    private Resume resume4 = new Resume(UUID_4);

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
        Resume resume5 = new Resume(resume1.getUuid());
        storage.update(resume5);
        Resume updatedResume = storage.get(resume5.getUuid());
        Assert.assertThat(updatedResume, is(resume1));
    }

    @Test
    public void save() {
        storage.save(resume4);
        Resume existResume = storage.get(resume4.getUuid());
        Assert.assertEquals(existResume, resume4);
        Assert.assertEquals(4, storage.size());
    }

    @Test
    public void get() {
        Resume resume = storage.get(resume1.getUuid());
        Assert.assertEquals(resume, resume1);
    }

    @Test(expected = NotExistStorageException.class)
    public void delete() {
        storage.delete(resume3.getUuid());
        Assert.assertEquals(2, storage.size());
        storage.get(resume3.getUuid());
    }

    @Test
    public void getAll() {
        Resume[] array = new Resume[]{resume1, resume2, resume3};
        Resume[] getAll = storage.getAll();
        Assert.assertArrayEquals(getAll, array);
        Assert.assertEquals(getAll.length, storage.size());
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

    @Test
    public void updateNotExist() {
        try {
            storage.update(resume4);
        } catch (NotExistStorageException ex) {
            System.out.println("Resume " + resume4.getUuid() + " not exist");
        }
    }

    @Test
    public void saveExist() {
        try {
            storage.delete(resume4.getUuid());
        } catch (NotExistStorageException ex) {
            System.out.println("Resume " + resume4.getUuid() + " not exist");
        }
    }

    @Test
    public void deleteNotExist() {
        try {
            storage.delete(resume4.getUuid());
        } catch (NotExistStorageException ex) {
            System.out.println("Resume " + resume4.getUuid() + " not exist");
        }
    }

    @Test
    public void getNotExist() {
        try {
            storage.get(resume4.getUuid());
        } catch (NotExistStorageException ex) {
            System.out.println("Resume " + resume4.getUuid() + " not exist");
        }
    }
}
