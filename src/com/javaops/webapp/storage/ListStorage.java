package com.javaops.webapp.storage;

import com.javaops.webapp.model.Resume;

import java.util.*;

public class ListStorage extends AbstractStorage {
    private final List<Resume> storage = new ArrayList<>();

    @Override
    public int size() {
        return storage.size();
    }

    @Override
    protected int getIndex(Resume r) {
        for (int i = 0; i < storage.size(); i++) {
            if (storage.get(i).equals(r)) return i;
        }
        return -1;
    }

    @Override
    protected void clearResumes() {
        storage.clear();
    }

    @Override
    protected List<Resume> ResumesGetAll() {
        return storage;
    }

    @Override
    protected boolean isExist(Object searchKey) {
        return storage.contains(searchKey);
    }

    @Override
    protected void doSave(Object getExistingSearchKey, Resume r) {
        storage.add(r);
    }

    @Override
    protected void doDelete(Object getNotExistingSearchKey, Resume r) {
        storage.remove(r);
    }

    @Override
    protected Resume doGet(Object getNotExistingSearchKey, String uuid) {
        return storage.get(getIndex((Resume) getNotExistingSearchKey));
    }

    @Override
    protected void doUpdate(Object getNotExistingSearchKey, Resume r) {
        storage.set(getIndex((Resume) getNotExistingSearchKey),r);
    }


}
