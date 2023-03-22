package com.javaops.webapp.storage;

import com.javaops.webapp.model.Resume;

import java.util.Arrays;

public class SortedArrayStorage extends AbstractArrayStorage {

    @Override
    protected void savePart(Resume r) {
        storage[size] = storage[(getIndex(r.getUuid()) * -1) - 1];
        storage[(getIndex(r.getUuid()) * -1) - 1] = r;
    }

    @Override
    protected void deletePart(String uuid) {
        for (int j = getIndex(uuid); j < size - 1; j++) {
            storage[j] = storage[j + 1];
        }
    }

    @Override
    protected int getIndex(String uuid) {
        Resume searchKey = new Resume();
        searchKey.setUuid(uuid);
        return Arrays.binarySearch(storage, 0, size, searchKey);
    }


}
