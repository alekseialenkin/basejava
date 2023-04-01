package com.javaops.webapp.storage;

import com.javaops.webapp.exception.ExistStorageException;
import com.javaops.webapp.exception.NotExistStorageException;
import com.javaops.webapp.model.Resume;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public abstract class AbstractStorageTest {
    protected final Storage storage;
    protected static final String UUID_1 = "uuid1";
    protected static final String UUID_2 = "uuid2";
    protected static final String UUID_3 = "uuid3";
    protected static final String FULLNAME_1 = "Petrov Ivan";
    protected static final String FULLNAME_2 = "Sidorov Fedor";
    protected static final String FULLNAME_3 = "Ivanova Mariya";

    protected static final Resume r1 = new Resume(UUID_1, FULLNAME_1);
    protected static final Resume r2 = new Resume(UUID_2, FULLNAME_2);
    protected static final Resume r3 = new Resume(UUID_3, FULLNAME_3);
    protected static final Resume r4 = new Resume("uuid4", "Ivan Ivanov");
    protected final Resume dummy = new Resume("dummy", "Dummy");

    protected AbstractStorageTest(Storage storage) {
        this.storage = storage;
    }

    @BeforeEach
    public void setUp() throws Exception {
        storage.clear();
        storage.save(r1);
        storage.save(r2);
        storage.save(r3);
    }

    protected void assertSize(int size) {
    }

    @Test
    public void clear() throws Exception {
        storage.clear();
        assertSize(storage.size());
        Resume [] r = storage.getAllSorted().toArray(new Resume[0]);
        Assertions.assertArrayEquals(new Resume[0], r);
    }

    @Test
    public void update() throws Exception {
        storage.update(r2);
        Assertions.assertSame(r2, storage.get(r2));
    }

    @Test
    public void getAllSorted() throws Exception {
        Resume[] storageTest = storage.getAllSorted().toArray(new Resume[0]);
        Assertions.assertArrayEquals(storageTest, storage.getAllSorted().toArray(new Resume[0]));
    }

    @Test
    public void save() throws Exception {
        storage.save(r4);
        assertGet(r4);
        assertSize(storage.size());
        Resume[] storageTest = storage.getAllSorted().toArray(new Resume[0]);
        Assertions.assertArrayEquals(storageTest,storage.getAllSorted().toArray(new Resume[0]));
    }

    @Test
    public void delete() throws Exception {
        storage.delete(r2);
        assertSize(storage.size());
        Resume[] storageTest = storage.getAllSorted().toArray(new Resume[0]);
        Assertions.assertArrayEquals(storageTest,storage.getAllSorted().toArray(new Resume[0]));
        Assertions.assertThrows(NotExistStorageException.class, () -> storage.get(r2));
    }

    public void assertGet(Resume resume) {
        Assertions.assertEquals(resume, storage.get(resume));
    }

    @Test
    public void get() throws Exception {
        assertGet(r1);
        assertGet(r2);
        assertGet(r3);
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
    public void getNotExist() throws Exception {
        Assertions.assertThrows(NotExistStorageException.class, () -> storage.get(dummy));
    }
}