package com.javaops.webapp.storage;

import com.javaops.webapp.model.Resume;

import java.util.Arrays;

public abstract class AbstractArrayStorage extends AbstractStorage {
    protected int size = 0;
    protected static final int STORAGE_LIMIT = 10000;
    protected final Resume[] storage = new Resume[STORAGE_LIMIT];

    public final int size() {
        return size;
    }


    protected final Resume[] ResumesGetAll() {
        return Arrays.copyOfRange(storage, 0, size);
    }

    protected final void clearResumes() {
        Arrays.fill(storage, 0, size, null);
        size = 0;
    }

    protected final void updateResume(int index, Resume r) {
        storage[index] = r;
    }

    protected final void sizeReduction() {
        storage[size - 1] = null;
        size--;
    }

    protected final void sizeIncrease() {
        size++;
    }
    public Resume getResume(int index){
        return storage[index];
    }

}

