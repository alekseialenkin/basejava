package com.javaops.webapp.storage;

import com.javaops.webapp.model.Resume;

import java.util.List;

public interface Storage {
    void clear();

    void update(Resume r);

    void save(Resume r);

    Resume get(Resume r);

    void delete(Resume r);

    List<Resume> getAllSorted();

    int size();
}
