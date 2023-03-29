package com.javaops.webapp.storage;

import com.javaops.webapp.model.Resume;

import java.util.Map;
import java.util.TreeMap;

public class MapStorage extends AbstractStorage {
    Map<String, Resume> mapStorage = new TreeMap<>();

    @Override
    protected int getIndex(String uuid) {
        return 0;
    }

    @Override
    protected void clearResumes() {
        mapStorage.clear();
    }

    @Override
    protected Resume[] ResumesGetAll() {
        return mapStorage.values().toArray(new Resume[0]);
    }

    @Override
    protected boolean isExist(Object searchKey) {
        return mapStorage.containsKey(searchKey.toString());
    }

    @Override
    protected void doSave(Object getExistingSearchKey, Resume r) {
        mapStorage.put(r.getUuid(),r);
    }

    @Override
    protected void doDelete(Object getNotExistingSearchKey, String uuid) {
        mapStorage.remove(uuid);
    }

    @Override
    protected Resume doGet(Object getNotExistingSearchKey, String uuid) {
        return mapStorage.get(uuid);
    }

    @Override
    protected void doUpdate(Object getNotExistingSearchKey, Resume r) {
        mapStorage.put(getNotExistingSearchKey.toString(),r);
    }
}
