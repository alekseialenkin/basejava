package com.javaops.webapp.storage;

import com.javaops.webapp.exception.ExistStorageException;
import com.javaops.webapp.exception.NotExistStorageException;
import com.javaops.webapp.exception.StorageException;
import com.javaops.webapp.model.Resume;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public abstract class AbstractArrayStorageTest {
    private final Storage storage;

    public AbstractArrayStorageTest(Storage storage) {
        this.storage = storage;
    }

    private static final String UUID_1 = "uuid1";
    private static final String UUID_2 = "uuid2";
    private static final String UUID_3 = "uuid3";
    Resume r1 = new Resume(UUID_1);
    Resume r2 = new Resume(UUID_2);
    Resume r3 = new Resume(UUID_3);

    @BeforeEach
    public void setUp() throws Exception {
        storage.clear();
        storage.save(r1);
        storage.save(r2);
        storage.save(r3);
    }

    @Test
    public void size() throws Exception {
        Assertions.assertEquals(3, storage.size());
    }

    @Test
    public void clear() throws Exception {
        storage.clear();
        Assertions.assertEquals(0, storage.size());
    }

    @Test
    public void update() throws Exception {
        storage.update(r2);
        Assertions.assertEquals(r2, storage.get(r2.getUuid()));
    }

    @Test
    public void getAll() throws Exception {
        Resume[] storageTest = storage.getAll();
        Assertions.assertEquals(r1, storageTest[0]);
        Assertions.assertEquals(r2, storageTest[1]);
        Assertions.assertEquals(r3, storageTest[2]);
    }

    @Test
    public void save() throws Exception {
        Resume r4 = new Resume("uuid4");
        storage.save(r4);
        Resume[] storageTest = storage.getAll();
        Assertions.assertEquals(r4, storageTest[3]);
    }

    @Test
    public void delete() throws Exception {
        storage.delete(r2.getUuid());
        Resume[] storageTest = storage.getAll();
        Assertions.assertEquals(r1, storageTest[0]);
        Assertions.assertEquals(r3, storageTest[1]);
    }

    @Test
    public void get() throws Exception {
        Resume r = storage.get(r1.getUuid());
        Assertions.assertEquals(r, r1);
    }

    @Test
    public void storageIsFull()  {
        try {
            for (int i = 3; i < 10000; i++){
                storage.save(new Resume("dummy"));
            }
        }catch (StorageException storageException){
            Assertions.fail("Storage is full earlier than it need");
        }
        Assertions.assertThrows(StorageException.class, () -> storage.save(new Resume()));
    }

    @Test
    public void exist() {
        Assertions.assertThrows(ExistStorageException.class, () -> storage.save(r2));
    }

    @Test
    public void getNotExist() throws Exception {
        Assertions.assertThrows(NotExistStorageException.class, () -> storage.get("dummy"));
    }
}
