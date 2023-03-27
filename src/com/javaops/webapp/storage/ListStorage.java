package com.javaops.webapp.storage;

import com.javaops.webapp.model.Resume;

import java.util.ArrayList;
import java.util.List;

public class ListStorage extends AbstractStorage {
    List<Resume> storageList = new ArrayList<>();

    @Override
    protected int getIndex(String uuid) {
        Resume searchKey = new Resume(uuid);
        return storageList.indexOf(searchKey);
    }

    @Override
    protected void deleteResume(int index) {
        storageList.remove(index);
    }

    @Override
    protected void saveResume(Resume r, int index) {
        storageList.add(r);
    }

    @Override
    protected Resume getResume(int index) {
        return storageList.get(index);
    }

    @Override
    protected void clearResumes() {
        storageList.clear();
    }

    @Override
    protected void updateResume(int index, Resume r) {
        storageList.set(index,r);
    }

    @Override
    protected Resume[] ResumesGetAll() {
        return storageList.toArray(new Resume[0]);
    }


}
