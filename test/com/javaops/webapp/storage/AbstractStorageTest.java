package com.javaops.webapp.storage;

import com.javaops.webapp.Config;
import com.javaops.webapp.exception.ExistStorageException;
import com.javaops.webapp.exception.NotExistStorageException;
import com.javaops.webapp.model.Resume;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;

public abstract class AbstractStorageTest {
    protected final static File STORAGEDIR = Config.get().getStorageDir();
    protected final Storage storage;
    protected static final String UUID_1 = "uuid1";
    protected static final String UUID_2 = "uuid2";
    protected static final String UUID_3 = "uuid3";
    protected static final String FULLNAME_1 = "Petrov Ivan";
    protected static final String FULLNAME_2 = "Sidorov Fedor";
    protected static final String FULLNAME_3 = "Ivanova Mariya";
    protected static final Resume r1;
    protected static final Resume r2;
    protected static final Resume r3;
    protected static final Resume r4 = new Resume("uuid4", "Ivan Ivanov");
    protected final Resume dummy = new Resume("dummy", "Dummy");

    static {
        r1 = new Resume(UUID_1, FULLNAME_1);
        r2 = new Resume(UUID_2, FULLNAME_2);
        r3 = new Resume(UUID_3, FULLNAME_3);
//        r1.addContact(ContactType.EMAIL, "mail1@ya.ru");
//        r1.addContact(ContactType.PHONE_NUMBER, "11111");
//        r1.addSection(SectionType.OBJECTIVE, new TextSection("Objective1"));
//        r1.addSection(SectionType.PERSONAL, new TextSection("Personal data"));
//        r1.addSection(SectionType.ACHIEVEMENT, new ListSection("Achivment11", "Achivment12", "Achivment13"));
//        r1.addSection(SectionType.QUALIFICATIONS, new ListSection("Java", "SQL", "JavaScript"));
//        r1.addSection(SectionType.EXPERIENCE,
//                new CompanySection(
//                        new Company("Company11", "http://Company11.ru",
//                                new Company.Period(2005, Month.JANUARY, "Period1", "content1"),
//                                new Company.Period(2001, Month.MARCH, 2005, Month.JANUARY, "Period2", "content2"))));
//        r1.addSection(SectionType.EDUCATION,
//                new CompanySection(
//                        new Company("Institute", null,
//                                new Company.Period(1996, Month.JANUARY, 2000, Month.DECEMBER, "aspirant", null),
//                                new Company.Period(2001, Month.MARCH, 2005, Month.JANUARY, "student", "IT facultet")),
//                        new Company("Company12", "http://Company12.ru")));
//        r2.addContact(ContactType.SKYPE, "skype2");
//        r2.addContact(ContactType.PHONE_NUMBER, "22222");
//        r1.addSection(SectionType.EXPERIENCE,
//                new CompanySection(
//                        new Company("Company2", "http://Company2.ru",
//                                new Company.Period(2015, Month.JANUARY, "Period1", "content1"))));
    }

    protected AbstractStorageTest(Storage storage) {
        this.storage = storage;
    }

    @BeforeEach
    public void setUp() {
        storage.clear();
        storage.save(r1);
        storage.save(r2);
        storage.save(r3);
    }

    protected void assertSize(int size) {
    }

    @Test
    public void clear() {
        storage.clear();
        assertSize(storage.size());
        Resume[] r = storage.getAllSorted().toArray(new Resume[0]);
        Assertions.assertArrayEquals(new Resume[0], r);
    }

    @Test
    public void update() {
        Resume newResume = new Resume(UUID_2, FULLNAME_2);
        storage.update(newResume);
        Assertions.assertEquals(newResume, storage.get(r2));
    }

    @Test
    public void getAllSorted() {
        Resume[] storageTest = storage.getAllSorted().toArray(new Resume[0]);
        Assertions.assertArrayEquals(storageTest, storage.getAllSorted().toArray(new Resume[0]));
    }

    @Test
    public void save() {
        storage.save(r4);
        assertGet(r4);
        assertSize(storage.size());
        Resume[] storageTest = storage.getAllSorted().toArray(new Resume[0]);
        Assertions.assertArrayEquals(storageTest, storage.getAllSorted().toArray(new Resume[0]));
    }

    @Test
    public void delete() {
        storage.delete(r2);
        assertSize(storage.size());
        Resume[] storageTest = storage.getAllSorted().toArray(new Resume[0]);
        Assertions.assertArrayEquals(storageTest, storage.getAllSorted().toArray(new Resume[0]));
        Assertions.assertThrows(NotExistStorageException.class, () -> storage.get(r2));
    }

    public void assertGet(Resume resume) {
        Assertions.assertEquals(resume, storage.get(resume));
    }

    @Test
    public void get() {
        assertGet(r2);
        assertGet(r3);
        assertGet(r1);
    }

    @Test
    public void saveExist() {
        Assertions.assertThrows(ExistStorageException.class, () -> storage.save(r2));
    }

    @Test
    public void updateNotExist() {
        Assertions.assertThrows(NotExistStorageException.class, () -> storage.update(dummy));
    }

    @Test
    public void deleteNotExist() {
        Assertions.assertThrows(NotExistStorageException.class, () -> storage.delete(dummy));
    }

    @Test
    public void getNotExist() {
        Assertions.assertThrows(NotExistStorageException.class, () -> storage.get(dummy));
    }
}