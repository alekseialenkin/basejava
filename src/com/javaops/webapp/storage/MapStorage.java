package com.javaops.webapp.storage;

import com.javaops.webapp.model.Resume;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MapStorage extends AbstractStorage {
    private final Map<String, Resume> storage = new HashMap<>();

    @Override
    public int size() {
        return storage.size();
    }

    @Override
    protected int getIndex(Resume r) {
        return 0;
    }

    @Override
    protected void clearResumes() {
        storage.clear();
    }

    @Override
    protected List<Resume> ResumesGetAll() {
        return Arrays.asList(storage.values().toArray(new Resume[0]));
    }

    @Override
    protected boolean isExist(Object searchKey) {
        Resume r = (Resume) searchKey;
        return storage.containsKey(r.getUuid());
    }

    @Override
    protected void doSave(Object getExistingSearchKey, Resume r) {
        storage.put(r.getUuid(), r);
    }

    @Override
    protected void doDelete(Object getNotExistingSearchKey, Resume r ) {
        storage.remove(r.getUuid());
    }

    @Override
    protected Resume doGet(Object getNotExistingSearchKey, String uuid) {
        return storage.get(uuid);
    }

    @Override
    protected void doUpdate(Object getNotExistingSearchKey, Resume r) {
        storage.put(getNotExistingSearchKey.toString(), r);
    }
}
