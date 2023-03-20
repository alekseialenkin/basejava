package com.javaops.webapp.storage;

import com.javaops.webapp.model.Resume;

import java.util.Arrays;

public class SortedArrayStorage extends AbstractArrayStorage {
    @Override
    public void update(Resume r) {
        if (getIndex(r.getUuid()) >= 0) {
            storage[getIndex(r.getUuid())].setUuid(r.getUuid());
        } else {
            System.out.println("Uuid is missing from the storage: " + r.getUuid());
        }
    }

    @Override
    public void save(Resume r) {
        if (size == STORAGE_LIMIT) {
            System.out.println("Storage is full");
        } else if (getIndex(r.getUuid()) >= 0) {
            System.out.println("Resume is in the storage: " + r.getUuid());
        } else {
            storage[size] = storage[(getIndex(r.getUuid()) * -1) - 1];
            storage[(getIndex(r.getUuid()) * -1) - 1] = r;
            size++;
        }
    }

    @Override
    protected int getIndex(String uuid) {
        Resume searchKey = new Resume();
        searchKey.setUuid(uuid);
        return Arrays.binarySearch(storage, 0, size, searchKey);
    }

    @Override
    public void delete(String uuid) {
        if (getIndex(uuid) < 0) {
            System.out.println("Uuid is missing from the storage: " + uuid);
        } else {
            storage[getIndex(uuid)] = storage[size - 1];
            storage[size - 1] = null;
            size--;
        }
    }
}
