package com.urise.webapp.storage;

import com.urise.webapp.Config;
import com.urise.webapp.ResumeTestData;
import com.urise.webapp.exception.ExistStorageException;
import com.urise.webapp.exception.NotExistStorageException;
import com.urise.webapp.model.ListSection;
import com.urise.webapp.model.Resume;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.util.*;

import static com.urise.webapp.model.ContactType.*;
import static com.urise.webapp.model.SectionType.ACHIEVEMENT;
import static com.urise.webapp.model.SectionType.QUALIFICATION;
import static org.junit.Assert.assertEquals;

public abstract class AbstractStorageTest {
    private static final Comparator<Resume> RESUME_COMPARATOR = Comparator.comparing(Resume::getFullName).thenComparing(Resume::getUuid);
    protected static final File STORAGE_DIR = Config.get().getStorageDir();

    protected Storage storage;

    public static final String UUID_1 = UUID.randomUUID().toString();
    public static final String UUID_2 = UUID.randomUUID().toString();
    public static final String UUID_3 = UUID.randomUUID().toString();
    public static final String UUID_4 = UUID.randomUUID().toString();
    public static final Resume RESUME1;
    public static final Resume RESUME2;
    public static final Resume RESUME3;
    public static final Resume RESUME4;

    static {
        RESUME1 = ResumeTestData.getFirstResume();
        RESUME2 = ResumeTestData.getSecondResume();
        RESUME3 = ResumeTestData.getThirdResume();
        RESUME4 = ResumeTestData.getFourthResume();
    }

    protected AbstractStorageTest(Storage storage) {
        this.storage = storage;
    }

    @Before
    public void setUp() {
        storage.clear();
        storage.save(RESUME1);
        storage.save(RESUME2);
        storage.save(RESUME3);
    }

    @Test
    public void clear() {
        storage.clear();
        assertSize(0);
    }

    @Test
    public void update() {
        Resume newResume = new Resume(UUID_2, "Name_5");
        newResume.addContact(MOBILE, "+7(999) 999-8888");
        newResume.addContact(SKYPE, "nikolay.girinovsky");
        newResume.addContact(MAIL, "ngirinovsky@yandex.ru");
        newResume.addContact(LINKEDIN, "https://www.linkedin.com/in/ngirinovsky");
        newResume.addContact(GITHUB, "https://github.com/ngirinovsky");
        newResume.addContact(STACKOVERFLOW, "https://stackoverflow.com/users/ngirinovsky");
        newResume.addContact(HOME_PAGE, "http://ngirinovsky.ru/");
        List<String> achievements = new ArrayList<>();
        achievements.add("101+ сертификатов по уборке");
        newResume.addSection(ACHIEVEMENT, new ListSection(achievements));
        List<String> qualifications = new ArrayList<>();
        qualifications.add("Швабра, тряпки, пена и все прибамбасы");
        newResume.addSection(QUALIFICATION, new ListSection(qualifications));
//        List<Organization> organizationList = new ArrayList<>();
//        organizationList.add(new Organization(
//                new OrganizationLink("DvorProduction", "http://DvorProduction.ru/")
//                , Collections.singletonList(new Organization.Stages(LocalDate.of(1992, 10, 1)
//                , NOW
//                , "Генеральный директор клининга"
//                , "Уборка территории"))));
//        newResume.addSection(EXPERIENCE, new OrganizationSection(organizationList));
        storage.update(newResume);
        assertEquals(newResume, storage.get(UUID_2));
    }

    @Test(expected = NotExistStorageException.class)
    public void updateNotExist() {
        storage.update(RESUME4);
    }

    @Test
    public void save() {
        storage.save(RESUME4);
        assertSize(4);
        assertEquals(RESUME4, storage.get(UUID_4));
        System.out.println(RESUME4);
        System.out.println(storage.get(UUID_4));
    }

    @Test(expected = ExistStorageException.class)
    public void saveExist() {
        storage.save(RESUME3);
    }

    @Test
    public void get() {
        Resume resume = storage.get(UUID_2);
        assertEquals(RESUME2, resume);
    }

    @Test(expected = NotExistStorageException.class)
    public void getNotExist() {
        storage.get(UUID_4);
    }

    @Test(expected = NotExistStorageException.class)
    public void delete() {
        storage.delete(UUID_3);
        assertSize(2);
        storage.get(UUID_3);
    }

    @Test(expected = NotExistStorageException.class)
    public void deleteNotExist() {
        storage.delete(UUID_4);
    }

    @Test
    public void getAllSorted() {
        List<Resume> list = Arrays.asList(RESUME1,RESUME2,RESUME3);
        list.sort(RESUME_COMPARATOR);
        List<Resume> getAll = storage.getAllSorted();
        System.out.println(getAll.equals(list));
        Assert.assertEquals(getAll, list);
    }

    @Test
    public void size() {
        assertSize(3);
    }

    private void assertSize(int size) {
        assertEquals(size, storage.size());
    }
}