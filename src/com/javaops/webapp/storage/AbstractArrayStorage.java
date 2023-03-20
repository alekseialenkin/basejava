package com.javaops.webapp.storage;

import com.javaops.webapp.model.Resume;

import java.util.Arrays;

public abstract class AbstractArrayStorage implements Storage {
    protected int size = 0;
    protected static final int STORAGE_LIMIT = 10000;
    protected final Resume[] storage = new Resume[STORAGE_LIMIT];

    public int size() {
        return size;
    }

    public Resume get(String uuid) {
        if (getIndex(uuid) < 0) {
            System.out.println("Uuid is missing from the storage: " + uuid);
            return null;
        }
        return storage[getIndex(uuid)];
    }
    public Resume[] getAll() {
        return Arrays.copyOfRange(storage, 0, size);
    }
    public void clear() {
        Arrays.fill(storage, 0, size, null);
        size = 0;
    }
    protected abstract int getIndex(String uuid);
}

