package com.javaops.webapp.storage;

import com.javaops.webapp.model.Resume;

/**
 * Array based storage for Resumes
 */
public class ArrayStorage extends AbstractArrayStorage {

    public void save(Resume r) {
        if (size == STORAGE_LIMIT) {
            System.out.println("Storage is full");
        } else if (getIndex(r.getUuid()) > -1) {
            System.out.println("Resume is in the storage: " + r.getUuid());
        } else {
            storage[size] = r;
            size++;
        }
    }

    public void delete(String uuid) {
        if (getIndex(uuid) != -1) {
            storage[getIndex(uuid)] = storage[size - 1];
            storage[size - 1] = null;
            size--;
        } else {
            System.out.println("Uuid is missing from the storage: " + uuid);
        }
    }

    public void update(Resume r) {
        if (getIndex(r.getUuid()) != -1) {
            if (r == storage[getIndex(r.getUuid())]) {
                storage[getIndex(r.getUuid())].setUuid(r.getUuid());
            }
        } else {
            System.out.println("Uuid is missing from the storage: " + r.getUuid());
        }
    }

    @Override
    protected int getIndex(String uuid) {
        for (int i = 0; i < size; i++) {
            if (uuid.equals(storage[i].getUuid())) {
                return i;
            }
        }
        return -1;
    }
}
