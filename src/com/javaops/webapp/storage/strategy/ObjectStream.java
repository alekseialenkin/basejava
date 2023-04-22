package com.javaops.webapp.storage.strategy;

import com.javaops.webapp.exception.StorageException;
import com.javaops.webapp.model.Resume;

import java.io.*;

public class ObjectStream implements Serialization {
    private File directory;

    @Override
    public void doWrite(Resume r, OutputStream os) {
        try (ObjectOutputStream outputStream = new ObjectOutputStream(os)) {
            outputStream.writeObject(r);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Resume doRead(InputStream is) {
        try (ObjectInputStream ois = new ObjectInputStream(is)) {
            return (Resume) ois.readObject();
        } catch (ClassNotFoundException e) {
            throw new StorageException("Error read", null, e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
