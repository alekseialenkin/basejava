package com.javaops.webapp.storage;

import com.javaops.webapp.model.Resume;

import java.util.Arrays;
import java.util.Comparator;

public class SortedArrayStorage extends AbstractArrayStorage {

    @Override
    protected void saveResume(Resume r) {
        storage[size] = storage[(getIndex(r) * -1) - 1];
        storage[(getIndex(r) * -1) - 1] = r;
    }

    @Override
    protected void deleteResume(Resume r) {
        int indexMove = size - getIndex(r) - 1;
        System.arraycopy(storage, getIndex(r) + 1, storage, getIndex(r), indexMove);
    }

    @Override
    protected int getIndex(Resume r) {
        return Arrays.binarySearch(storage, 0, size, r, RESUME_COMPARATOR);
    }

    private static final Comparator<Resume> RESUME_COMPARATOR = Comparator.comparing(Resume::getUuid);

}
