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
        int index = getIndex(r.getUuid());
        if (index < 0) {
            throw new NotExistStorageException(r.getUuid());
        } else {
            updateResume(index, r);
        }
    }

    @Override
    public final void save(Resume r) {
        int index = getIndex(r.getUuid());
        if (index >= 0) {
            throw new ExistStorageException(r.getUuid());
        }
        else if (size() == AbstractArrayStorage.STORAGE_LIMIT){
           throw new  StorageException("Storage overflow", r.getUuid());
        }
        else {
            saveResume(r, index);
            sizeIncrease();
        }
    }

    @Override
    public final Resume get(String uuid) {
        int index = getIndex(uuid);
        if (index < 0) {
            throw new NotExistStorageException(uuid);
        }
        return getResume(index);
    }

    @Override
    public final void delete(String uuid) {
        int index = getIndex(uuid);
        if (index < 0) {
            throw new NotExistStorageException(uuid);
        } else {
            deleteResume(index);
            sizeReduction();
        }
    }


    @Override
    public final Resume[] getAll() {
        return ResumesGetAll();
    }

    @Override
    public int size(){return 0;}

    protected abstract int getIndex(String uuid);

    protected abstract void deleteResume(int index);

    protected  void sizeReduction(){}

    protected abstract void saveResume(Resume r, int index);

    protected  void sizeIncrease(){}

    protected abstract Resume getResume(int index);

    protected abstract void clearResumes();
    protected abstract void updateResume(int index, Resume r);
    protected abstract Resume[] ResumesGetAll();
}
