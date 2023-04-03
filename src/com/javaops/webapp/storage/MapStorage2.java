package com.javaops.webapp.storage;

import com.javaops.webapp.model.Resume;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MapStorage2 extends AbstractStorage {
    private final Map<String, Resume> storage = new HashMap<>();

    @Override
    public int size() {
        return storage.size();
    }

    @Override
    protected Object getSearchKey(Object searchKey) {
        return null;
    }

    @Override
    protected void clearResumes() {
        storage.clear();
    }

    @Override
    protected List<Resume> doGetAll() {
        return Arrays.asList(storage.values().toArray(new Resume[0]));
    }

    @Override
    protected boolean isExist(Object searchKey) {
        return storage.containsValue(searchKey);
    }

    @Override
    protected void doSave(Object searchKey, Resume r) {
        storage.put(r.getUuid(),r);
    }

    @Override
    protected void doDelete(Object searchKey, Resume r) {
        storage.remove(r);
    }

    @Override
    protected Resume doGet(Object searchKey, String uuid) {
        return storage.get(uuid);
    }

    @Override
    protected void doUpdate(Object searchKey, Resume r) {

    }
}
