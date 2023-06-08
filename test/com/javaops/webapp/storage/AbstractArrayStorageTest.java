package com.javaops.webapp.storage;

import com.javaops.webapp.exception.StorageException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static com.javaops.webapp.TestData.dummy;

public abstract class AbstractArrayStorageTest extends AbstractStorageTest {
    public AbstractArrayStorageTest(Storage storage) {
        super(storage);
    }
    @Test
    protected final void saveOverflow() {
        try {
            storage.clear();
            for (int i = 0; i < AbstractArrayStorage.STORAGE_LIMIT; i++) {
                storage.save(dummy);
            }
        } catch (StorageException storageException) {
            Assertions.fail("Storage is full earlier than it need");
        }
        Assertions.assertThrows(StorageException.class, () -> storage.save(dummy));
    }
    public final void assertSize(int size) {
        Assertions.assertEquals(size, storage.size());
    }

    @Test
    public void size() throws Exception {
        assertSize(storage.size());
    }

}
