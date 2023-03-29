package com.javaops.webapp.storage;

import com.javaops.webapp.model.Resume;

import java.util.Arrays;

public class SortedArrayStorage extends AbstractArrayStorage {

    @Override
    protected void saveResume(Resume r) {
        storage[size] = storage[(getIndex(r.getUuid()) * -1) - 1];
        storage[(getIndex(r.getUuid()) * -1) - 1] = r;
    }

    @Override
    protected void deleteResume(String uuid) {
        int indexMove = size - getIndex(uuid) - 1;
        System.arraycopy(storage, getIndex(uuid) + 1, storage, getIndex(uuid), indexMove);
    }

    @Override
    protected int getIndex(String uuid) {
        Resume searchKey = new Resume(uuid);
        return Arrays.binarySearch(storage, 0, size, searchKey);
    }


}
