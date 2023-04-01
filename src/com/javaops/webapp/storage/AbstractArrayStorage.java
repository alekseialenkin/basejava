package com.javaops.webapp.storage;

import com.javaops.webapp.model.Resume;

import java.util.Arrays;
import java.util.List;

public abstract class AbstractArrayStorage extends AbstractStorage {
    protected int size = 0;
    protected static final int STORAGE_LIMIT = 10000;
    protected final Resume[] storage = new Resume[STORAGE_LIMIT];

    public final int size() {
        return size;
    }

    protected void doSave(Object getExistingSearchKey, Resume r) {
        saveResume(r);
        sizeIncrease();
    }

    protected void doDelete(Object getNotExistingSearchKey, Resume r) {
        deleteResume(r);
        sizeReduction();
    }

    protected Resume doGet(Object getNotExistingSearchKey, String uuid) {
        return getResume(getNotExistingSearchKey);
    }

    protected void doUpdate(Object getNotExistingSearchKey, Resume r) {
        updateResume(r);
    }

    protected final List<Resume> ResumesGetAll() {
        return Arrays.asList(Arrays.copyOfRange(storage, 0, size));
    }

    protected final void clearResumes() {
        Arrays.fill(storage, 0, size, null);
        size = 0;
    }

    protected final void updateResume(Resume r) {
        storage[getIndex(r)] = r;
    }

    protected final void sizeReduction() {
        storage[size - 1] = null;
        size--;
    }

    protected final void sizeIncrease() {
        size++;
    }

    public Resume getResume(Object searchKey) {
        return storage[getIndex((Resume) searchKey)];
    }

    protected abstract void saveResume(Resume r);

    protected abstract void deleteResume(Resume r);

    protected boolean isExist(Object searchKey) {
        for (int i = 0; i < size; i++) {
            if (storage[i].equals(searchKey)) return true;
        }
        return false;
    }
}

