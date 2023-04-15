package com.javaops.webapp.storage;

import com.javaops.webapp.exception.StorageException;
import com.javaops.webapp.model.Resume;

import java.io.*;

public class ObjectStreamFileStorage extends AbstractFileStorage implements Serializable {

    protected ObjectStreamFileStorage(File directory) {
        super(directory);
    }

    @Override
    protected Resume doRead(InputStream file) throws IOException {
        try (ObjectInputStream is = new ObjectInputStream(file)) {
            return (Resume) is.readObject();
        } catch (ClassNotFoundException e) {
            throw new StorageException("Error read", null, e);
        }
    }

    @Override
    protected void doWrite(Resume r, OutputStream os) throws IOException {
        try (ObjectOutputStream outputStream = new ObjectOutputStream(os)) {
            outputStream.writeObject(r);
        }
    }

}
