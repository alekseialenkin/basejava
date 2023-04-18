package com.javaops.webapp.storage.strategy;

import com.javaops.webapp.model.Resume;

import java.io.InputStream;
import java.io.OutputStream;

public interface Serialization<SK> {
    void doWrite(Resume r, OutputStream os);

    Resume doRead(InputStream is);
}
