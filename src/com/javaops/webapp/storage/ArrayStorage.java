package com.javaops.webapp.storage;

import com.javaops.webapp.model.Resume;

import java.util.Arrays;

/**
 * Array based storage for Resumes
 */
public class ArrayStorage {
    private int size;
    private final int STORAGE_LIMIT = 10000;
    private final Resume[] storage = new Resume[STORAGE_LIMIT];

    private int getIndex(Resume r) {
        for (int i = 0; i < size; i++) {
            if (r.getUuid().equals(storage[i].getUuid())) {
                return i;
            }
        }
        return -1;
    }

    private int getIndex(String uuid) {
        for (int i = 0; i < size; i++) {
            if (uuid.equals(storage[i].getUuid())) {
                return i;
            }
        }
        return -1;
    }

    public void clear() {
        Arrays.fill(storage, null);
        size = 0;
    }

    public void save(Resume r) {
        if (size == storage.length) {
            System.out.println("Storage is full");
        } else if (getIndex(r) > -1) System.out.println("Resume is in the storage: " + r.getUuid());
        else {
            storage[size] = r;
            size++;
        }
    }

    public Resume get(String uuid) {
        if (getIndex(uuid) != -1) {
            return storage[getIndex(uuid)];
        }
        else System.out.println("Uuid is missing from the storage: " + uuid);
        return null;
    }

    public void delete(String uuid) {
        if (getIndex(uuid) != -1) {
            storage[getIndex(uuid)] = storage[size - 1];
            storage[size - 1] = null;
            size--;
        } else System.out.println("Uuid is missing from the storage: " + uuid);
    }


    /**
     * @return array, contains only Resumes in storage (without null)
     */
    public Resume[] getAll() {
        Resume[] resumes = new Resume[size];
        for (int i = 0; i < size; i++) {
            resumes[i] = storage[i];
        }
        return resumes;
    }

    public void update(Resume r) {
        if (getIndex(r) != -1) {
            if (r == storage[getIndex(r)]) {
                storage[getIndex(r)].setUuid(r.getUuid());
            }
        } else System.out.println("Uuid is missing from the storage: " + r.getUuid());
    }

    public int size() {
        return size;
    }
}
