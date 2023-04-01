package com.javaops.webapp.storage;

import com.javaops.webapp.model.Resume;

/**
 * Array based storage for Resumes
 */
public class ArrayStorage extends AbstractArrayStorage {

    @Override
    protected void saveResume(Resume r) {
        storage[size] = r;
    }

    @Override
    protected void deleteResume(Resume r ) {
        storage[getIndex(r)] = storage[size - 1];
    }

    @Override
    protected int getIndex(Resume r) {
        for (int i = 0; i < size; i++) {
            if (r.equals(storage[i])) {
                return i;
            }
        }
        return -1;
    }

}