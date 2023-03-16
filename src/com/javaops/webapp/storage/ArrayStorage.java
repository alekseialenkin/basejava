package com.javaops.webapp.storage;

import com.javaops.webapp.model.Resume;

/**
 * Array based storage for Resumes
 */
public class ArrayStorage {
    private int size;
    private Resume[] storage = new Resume[10000];

    private boolean isInStorage(Resume r){
        boolean is = false;
        for (int i = 0; i < size; i++) {
            if (r == storage[i]) {
                is = true;
                break;
            }
        }
        return is;
    }
    private boolean isInStorage (String uuid){
        boolean is = false;
        for (int i = 0; i < size; i++) {
            if (uuid.equals(storage[i].getUuid())) {
                is = true;
                break;
            }
        }
        return is;
    }

    public void clear() {
        for (int i = 0; i < size; i++) {
            storage[i] = null;
        }
        size = 0;
    }

    public void save(Resume r) {
        if (!isInStorage(r)) {
            if (size < storage.length) {
                storage[size] = r;
                size++;
            }
            else System.out.println("Storage is full");
        }
        else System.out.println("Resume is in the storage: " + r.getUuid());
    }

    public Resume get(String uuid) {
        if (isInStorage(uuid)) {
            for (int i = 0; i < size; i++) {
                if (uuid.equals(storage[i].toString())) {
                    return storage[i];
                }
            }
        }
        System.out.println("Uuid is missing from the storage: " + uuid);
        return null;
    }

    public void delete(String uuid) {
        if (isInStorage(uuid)) {
            for (int i = 0; i < size; i++) {
                if (uuid.equals(storage[i].toString())) {
                    storage[i] = storage[size - 1];
                    storage[size - 1] = null;
                    size--;
                    break;
                }
            }
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
        if (isInStorage(r)) {
            for (int i = 0; i < size; i++) {
                if (r == storage[i]) {
                    storage[i].setUuid(r.getUuid());
                    break;
                }
            }
        }
        else System.out.println("Uuid is missing from the storage: " + r.getUuid());
    }

    public int size() {
        return size;
    }
}
