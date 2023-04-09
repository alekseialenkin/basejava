package com.javaops.webapp.storage;

import com.javaops.webapp.model.Resume;

import java.util.Arrays;
import java.util.Comparator;

public class SortedArrayStorage extends AbstractArrayStorage {

    @Override
    protected void saveResume(Resume r) {
        storage[size] = storage[(getSearchKey(r.getUuid()) * -1) - 1];
        storage[ (getSearchKey(r.getUuid()) * -1) - 1] = r;
    }

    @Override
    protected void deleteResume(String uuid) {
        int indexMove = size - getSearchKey(uuid) - 1;
        System.arraycopy(storage, getSearchKey(uuid) + 1, storage, getSearchKey(uuid), indexMove);
    }

    @Override
    protected Integer getSearchKey(String uuid) {
        Resume r = new Resume(uuid, "");
        return Arrays.binarySearch(storage, 0, size, r, RESUME_COMPARATOR);
    }

    private static final Comparator<Resume> RESUME_COMPARATOR = Comparator.comparing(Resume::getUuid);

}
