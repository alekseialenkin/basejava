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
    protected void deleteResume(String uuid ) {
        storage[(int) getSearchKey(uuid)] = storage[size - 1];
    }

    @Override
    protected Object getSearchKey(Object searchKey) {
        for (int i = 0; i < size; i++) {
            if (searchKey.equals(storage[i].getUuid())) {
                return i;
            }
        }
        return -1;
    }

}