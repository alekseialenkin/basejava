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
    protected Object getSearchKey(Object searchKey) {
        Resume r = new Resume(searchKey.toString(),"d");
        for (int i = 0; i < storage.size(); i++) {
            if (storage.get(i).getUuid().equals(r.getUuid())) return storage.get(i);
        }
        return -1;
    }

    @Override
    protected void clearResumes() {
        storage.clear();
    }

    @Override
    protected List<Resume> doGetAll() {
        return storage;
    }

    @Override
    protected boolean isExist(Object searchKey) {
        return storage.contains(searchKey);
    }

    @Override
    protected void doSave(Object searchKey, Resume r) {
        storage.add(r);
    }

    @Override
    protected void doDelete(Object searchKey, Resume r) {
        storage.remove(r);
    }

    @Override
    protected Resume doGet(Object searchKEy, String uuid) {
        return (Resume) getSearchKey(uuid);
    }

    @Override
    protected void doUpdate(Object searchKey, Resume r) {
        storage.add(r);
    }


}
