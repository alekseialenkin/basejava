package com.javaops.webapp.storage;

import com.javaops.webapp.model.Resume;

import java.util.Arrays;

public abstract class AbstractArrayStorage implements Storage {
    protected int size = 0;
    protected static final int STORAGE_LIMIT = 10000;
    protected final Resume[] storage = new Resume[STORAGE_LIMIT];

    public final int size() {
        return size;
    }

    public final Resume get(String uuid) {
        if (getIndex(uuid) < 0) {
            System.out.println("Uuid is missing from the storage: " + uuid);
            return null;
        }
        return storage[getIndex(uuid)];
    }

    public final Resume[] getAll() {
        return Arrays.copyOfRange(storage, 0, size);
    }

    public final void clear() {
        Arrays.fill(storage, 0, size, null);
        size = 0;
    }

    public final void update(Resume r) {
        if (getIndex(r.getUuid()) >= 0) {
            if (r == storage[getIndex(r.getUuid())]) {
                storage[getIndex(r.getUuid())].setUuid(r.getUuid());
            }
        } else {
            System.out.println("Uuid is missing from the storage: " + r.getUuid());
        }
    }

    public final void save(Resume r) {
        if (size == STORAGE_LIMIT) {
            System.out.println("Storage is full");
        } else if (getIndex(r.getUuid()) >= 0) {
            System.out.println("Resume is in the storage: " + r.getUuid());
        } else {
            savePart(r);
            size++;
        }
    }

    public final void delete(String uuid) {
        if (getIndex(uuid) < 0) {
            System.out.println("Uuid is missing from the storage: " + uuid);
        } else {
            deletePart(uuid);
            storage[size - 1] = null;
            size--;
        }
    }

    protected abstract int getIndex(String uuid);

    protected abstract void deletePart(String uuid);

    protected abstract void savePart(Resume r);
}

