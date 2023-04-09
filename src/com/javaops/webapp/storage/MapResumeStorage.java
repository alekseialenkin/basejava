package com.javaops.webapp.storage;

import com.javaops.webapp.model.Resume;

import java.util.*;

public class MapResumeStorage extends AbstractStorage<Resume> {
    private final Map<String, Resume> storage = new HashMap<>();

    @Override
    public void clear() {
        storage.clear();
    }

    @Override
    public int size() {
        return storage.size();
    }

    @Override
    protected Resume getSearchKey(String uuid) {
        return storage.get(uuid);
    }

    @Override
    protected List<Resume> doGetAll() {
        return Arrays.asList(storage.values().toArray(new Resume[0]));
    }

    @Override
    protected boolean isExist(Resume searchKey) {
        return Objects.nonNull(searchKey);
    }

    @Override
    protected void doSave(Resume searchKey, Resume r) {
        storage.put(r.getUuid(), r);
    }

    @Override
    protected void doDelete(Resume searchKey, Resume r) {
        storage.remove(r.getUuid());
    }

    @Override
    protected Resume doGet(Resume searchKey, String uuid) {
        return storage.get(uuid);
    }

    @Override
    protected void doUpdate(Resume searchKey, Resume r) {

    }
}
