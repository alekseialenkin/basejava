package com.javaops.webapp.storage;

import com.javaops.webapp.model.Resume;

import java.util.Arrays;

public class SortedArrayStorage extends AbstractArrayStorage {

    @Override
    protected void saveResume(Resume r, int index) {
        storage[size] = storage[(index * -1) - 1];
        storage[(index * -1) - 1] = r;
    }

    @Override
    protected void deleteResume(int index) {
        int indexMove = size - index - 1;
        System.arraycopy(storage, index + 1, storage, index, indexMove);
    }

    @Override
    protected int getIndex(String uuid) {
        Resume searchKey = new Resume(uuid);
        return Arrays.binarySearch(storage, 0, size, searchKey);
    }


}
