package com.javaops.webapp.storage;

import com.javaops.webapp.model.Resume;

import java.util.ArrayList;
import java.util.List;

public class ListStorage extends AbstractStorage {
    List<Resume> storageList = new ArrayList<>();

    @Override
    protected int getIndex(String uuid) {
        Resume searchKey = new Resume(uuid);
        for (int i = 0; i < storageList.size(); i++) {
            if (storageList.get(i).getUuid().equals(searchKey.getUuid())) return i;
        }
        return -1;
    }

    @Override
    protected void clearResumes() {
        storageList.clear();
    }

    @Override
    protected Resume[] ResumesGetAll() {
        return storageList.toArray(new Resume[0]);
    }

    @Override
    protected boolean isExist(Object searchKey) {
        Resume r = new Resume(searchKey.toString());
        return storageList.contains(r);
    }

    @Override
    protected void doSave(Object getExistingSearchKey, Resume r) {
        storageList.add(r);
    }

    @Override
    protected void doDelete(Object getNotExistingSearchKey, String uuid) {
        Resume r = new Resume(uuid);
        storageList.remove(r);
    }

    @Override
    protected Resume doGet(Object getNotExistingSearchKey, String uuid) {
        return storageList.get(getIndex(getNotExistingSearchKey.toString()));
    }

    @Override
    protected void doUpdate(Object getNotExistingSearchKey, Resume r) {
        storageList.set(getIndex(getNotExistingSearchKey.toString()),r);
    }


}
