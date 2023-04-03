package com.javaops.webapp.storage;

import com.javaops.webapp.exception.ExistStorageException;
import com.javaops.webapp.exception.NotExistStorageException;
import com.javaops.webapp.model.Resume;

import java.util.List;

public abstract class AbstractStorage implements Storage {
    @Override
    public final void clear() {
        clearResumes();
    }

    @Override
    public final void update(Resume r) {
        doUpdate(getNotExistingSearchKey(r.getUuid()), r);
    }

    @Override
    public final void save(Resume r) {
        doSave(getExistingSearchKey(r.getUuid()), r);
    }

    @Override
    public final Resume get(Resume r) {
        return doGet(getNotExistingSearchKey(r.getUuid()), r.getUuid());
    }

    @Override
    public final void delete(Resume r) {
        doDelete(getNotExistingSearchKey(r.getUuid()), r);
    }

    @Override
    public final List<Resume> getAllSorted() {
        doGetAll().sort((o1, o2) -> {
            if (o1.getFullName().equals(o2.getFullName())) {
                return o1.getUuid().compareTo(o2.getUuid());
            }
            return o1.getFullName().compareTo(o2.getFullName());
        });
        return doGetAll();
    }

    @Override
    public abstract int size();

    private Object getExistingSearchKey(String uuid) {
        Object searchKey = getSearchKey(uuid);
        if (isExist(searchKey)) {
            throw new ExistStorageException(uuid);
        } else {
            return searchKey;
        }
    }

    private Object getNotExistingSearchKey(String uuid) {
        Object searchKey = getSearchKey(uuid);
        if (!isExist(searchKey)) {
            throw new NotExistStorageException(uuid);
        } else {
            return searchKey;
        }
    }

    protected abstract Object getSearchKey(Object searchKey);

    protected abstract void clearResumes();

    protected abstract List<Resume> doGetAll();

    protected abstract boolean isExist(Object searchKey);

    protected abstract void doSave(Object searchKey, Resume r);

    protected abstract void doDelete(Object searchKey, Resume r);

    protected abstract Resume doGet(Object searchKey, String uuid);

    protected abstract void doUpdate(Object searchKey, Resume r);
}
