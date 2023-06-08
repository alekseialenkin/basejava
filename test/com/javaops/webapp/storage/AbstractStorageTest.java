package com.javaops.webapp.storage;

import com.javaops.webapp.exception.ExistStorageException;
import com.javaops.webapp.exception.NotExistStorageException;
import com.javaops.webapp.model.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static com.javaops.webapp.TestData.*;

public abstract class AbstractStorageTest {
    protected final Storage storage;


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
        Resume newResume = new Resume(UUID_2, "New Name");
        newResume.addContact(ContactType.PHONE_NUMBER,"5555");
        newResume.addContact(ContactType.SKYPE,"discord");
        newResume.addSection(SectionType.ACHIEVEMENT,new ListSection("new achievement1","new achievement2"));
        newResume.addSection(SectionType.PERSONAL, new TextSection("new personal1"));
        storage.update(newResume);
        Assertions.assertEquals(newResume, storage.get(r2.getUuid()));
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
        storage.delete(UUID_2);
        assertSize(storage.size());
        Resume[] storageTest = storage.getAllSorted().toArray(new Resume[0]);
        Assertions.assertArrayEquals(storageTest, storage.getAllSorted().toArray(new Resume[0]));
        Assertions.assertThrows(NotExistStorageException.class, () -> storage.get(r2.getUuid()));
    }

    public void assertGet(Resume resume) {
        Assertions.assertEquals(resume, storage.get(resume.getUuid()));
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
        Assertions.assertThrows(NotExistStorageException.class, () -> storage.delete(dummy.getUuid()));
    }

    @Test
    public void getNotExist() {
        Assertions.assertThrows(NotExistStorageException.class, () -> storage.get(dummy.getUuid()));
    }
}