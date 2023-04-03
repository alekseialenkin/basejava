package com.javaops.webapp.storage;

import com.javaops.webapp.exception.StorageException;
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

    protected void doSave(Object searchKey, Resume r) {
        if (size == STORAGE_LIMIT) {
            throw new StorageException("Storage overflow", r.getUuid());
        }
        saveResume(r);
        sizeIncrease();
    }

    protected void doDelete(Object searchKey, Resume r) {
        deleteResume(r.getUuid());
        sizeReduction();
    }

    protected Resume doGet(Object searchKey, String uuid) {
        return getResume(uuid);
    }

    protected void doUpdate(Object searchKey, Resume r) {
        updateResume(r);
    }

    protected final List<Resume> doGetAll() {
        return Arrays.asList(Arrays.copyOfRange(storage, 0, size));
    }

    protected final void clearResumes() {
        Arrays.fill(storage, 0, size, null);
        size = 0;
    }

    protected final void updateResume(Resume r){
        storage[(int) getSearchKey(r.getUuid())] = r;
    }

    protected final void sizeReduction() {
        storage[size - 1] = null;
        size--;
    }

    protected final void sizeIncrease() {
        size++;
    }

    public Resume getResume(Object searchKey) {
        return storage[(int) getSearchKey(searchKey)];
    }

    protected abstract void saveResume(Resume r);

    protected abstract void deleteResume(String uuid);

    protected boolean isExist(Object searchKey) {
        return (Integer) searchKey >= 0;
    }
}

