package com.javaops.webapp.storage;

import com.javaops.webapp.model.Resume;

import java.util.Arrays;
import java.util.Comparator;

public class SortedArrayStorage extends AbstractArrayStorage {

    @Override
    protected void saveResume(Resume r) {
        storage[size] = storage[((Integer) getSearchKey(r.getUuid()) * -1) - 1];
        storage[((Integer)getSearchKey(r.getUuid()) * -1) - 1] = r;
    }

    @Override
    protected void deleteResume(String uuid) {
        int indexMove = size - (Integer)getSearchKey(uuid) - 1;
        System.arraycopy(storage, (Integer)getSearchKey(uuid) + 1, storage, (Integer)getSearchKey(uuid), indexMove);
    }

    @Override
    protected Object getSearchKey(Object searchKey) {
        Resume r = new Resume(searchKey.toString(),"");
        return Arrays.binarySearch(storage, 0, size, r, RESUME_COMPARATOR);
    }

    private static final Comparator<Resume> RESUME_COMPARATOR = Comparator.comparing(Resume::getUuid);

}
