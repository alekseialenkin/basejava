package com.javaops.webapp.storage;

import com.javaops.webapp.exception.ExistStorageException;
import com.javaops.webapp.exception.NotExistStorageException;
import com.javaops.webapp.exception.StorageException;
import com.javaops.webapp.model.Resume;

import java.util.List;

public abstract class AbstractStorage implements Storage {
    @Override
    public final void clear() {
        clearResumes();
    }

    @Override
    public final void update(Resume r) {
        doUpdate(getNotExistingSearchKey(r), r);
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
    public final Resume get(Resume r) {
        return doGet(getNotExistingSearchKey(r), r.getUuid());
    }

    @Override
    public final void delete(Resume r) {
        doDelete(getNotExistingSearchKey(r), r);
    }

    @Override
    public final List<Resume> getAllSorted() {
        ResumesGetAll().sort((o1, o2) -> {
            if (o1.getFullName().equals(o2.getFullName())) return o1.getUuid().compareTo(o2.getUuid());
            return o1.getFullName().compareTo(o2.getFullName());
        });
        return ResumesGetAll();
    }

    @Override
    public abstract int size();

    private Object getExistingSearchKey(Resume r) {
        if (isExist(r)) throw new ExistStorageException(r.getUuid());
        else {
            return r;
        }
    }

    private Object getNotExistingSearchKey(Resume r) {
        if (!isExist(r)) throw new NotExistStorageException(r.getUuid());
        else {
            return r;
        }
    }

    protected abstract int getIndex(Resume r);

    protected abstract void clearResumes();

    protected abstract List<Resume> ResumesGetAll();

    protected abstract boolean isExist(Object searchKey);

    protected abstract void doSave(Object getExistingSearchKey, Resume r);

    protected abstract void doDelete(Object getNotExistingSearchKey, Resume r);
    protected abstract Resume doGet(Object getNotExistingSearchKey, String uuid);

    protected abstract void doUpdate(Object getNotExistingSearchKey, Resume r);
}
