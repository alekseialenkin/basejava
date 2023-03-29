package com.javaops.webapp.storage;

import com.javaops.webapp.exception.ExistStorageException;
import com.javaops.webapp.exception.NotExistStorageException;
import com.javaops.webapp.exception.StorageException;
import com.javaops.webapp.model.Resume;

public abstract class AbstractStorage implements Storage {
    @Override
    public final void clear() {
        clearResumes();
    }

    @Override
    public final void update(Resume r) {
        doUpdate(getNotExistingSearchKey(r.getUuid()),r);
    }

    @Override
    public final void save(Resume r) {
        if (size() == AbstractArrayStorage.STORAGE_LIMIT) {
            throw new StorageException("Storage overflow", r.getUuid());
        } else {
            doSave(getExistingSearchKey(r), r);
        }
    }

    @Override
    public final Resume get(String uuid) {
        return doGet(getNotExistingSearchKey(uuid),uuid);
    }
    
    @Override
    public final void delete(String uuid) {
        doDelete(getNotExistingSearchKey(uuid),uuid);
    }


    @Override
    public final Resume[] getAll() {
        return ResumesGetAll();
    }

    @Override
    public int size() {
        return 0;
    }

    protected abstract int getIndex(String uuid);

    protected abstract void clearResumes();


    protected abstract Resume[] ResumesGetAll();

    protected abstract boolean isExist(Object searchKey);
    protected abstract void doSave(Object getExistingSearchKey, Resume r);
    protected abstract void doDelete(Object getNotExistingSearchKey, String uuid);
    protected abstract Resume doGet(Object getNotExistingSearchKey,String uuid);
    protected abstract void doUpdate(Object getNotExistingSearchKey, Resume r);

    private Object getExistingSearchKey(Resume r) {
        if (isExist(r)) throw new ExistStorageException(r.getUuid());
        else {
            return r;
        }
    }

    private Object getNotExistingSearchKey(String uuid) {
        if (!isExist(uuid)) throw new NotExistStorageException(uuid);
        else {
            return uuid;
        }
    }
}
