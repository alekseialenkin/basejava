package com.javaops.webapp.storage;

import com.javaops.webapp.exception.ExistStorageException;
import com.javaops.webapp.exception.NotExistStorageException;
import com.javaops.webapp.model.Resume;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public abstract class AbstractStorageTest {
    protected  final Storage storage;
    protected static final String UUID_1 = "uuid1";
    protected static final String UUID_2 = "uuid2";
    protected static final String UUID_3 = "uuid3";
    protected static final Resume r1 = new Resume(UUID_1);
    protected static final Resume r2 = new Resume(UUID_2);
    protected static final Resume r3 = new Resume(UUID_3);
    protected static final Resume r4 = new Resume("uuid4");
    protected final Resume dummy = new Resume("dummy");

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
    public void assertSize(int size) {}

    @Test
    public void clear() throws Exception {
        storage.clear();
        assertSize(storage.size());
        Assertions.assertArrayEquals(new Resume[0], storage.getAll());
    }

    @Test
    public void update() throws Exception {
        storage.update(r2);
        Assertions.assertSame(r2, storage.get(r2.getUuid()));
    }

    @Test
    public void getAll() throws Exception {
        Resume[] storageTest = {r1, r2, r3};
        Assertions.assertArrayEquals(storageTest, storage.getAll());
    }

    @Test
    public void save() throws Exception {
        storage.save(r4);
        assertGet(r4);
        assertSize(storage.size());
        Resume[] storageTest = storage.getAll();
        Assertions.assertEquals(r4, storageTest[3]);
    }

    @Test
    public void delete() throws Exception {
        storage.delete(r2.getUuid());
        assertSize(storage.size());
        Resume[] storageTest = storage.getAll();
        Assertions.assertEquals(r1, storageTest[0]);
        Assertions.assertEquals(r3, storageTest[1]);
        Assertions.assertThrows(NotExistStorageException.class, () -> storage.get(r2.getUuid()));
    }

    public void assertGet(Resume resume) {
        Assertions.assertEquals(resume, storage.get(resume.getUuid()));
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
        Assertions.assertThrows(NotExistStorageException.class, () -> storage.delete(dummy.getUuid()));
    }

    @Test
    public void getNotExist() throws Exception {
        Assertions.assertThrows(NotExistStorageException.class, () -> storage.get(dummy.getUuid()));
    }
}