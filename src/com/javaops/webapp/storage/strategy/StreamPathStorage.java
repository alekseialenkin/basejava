package com.javaops.webapp.storage.strategy;

import com.javaops.webapp.exception.StorageException;
import com.javaops.webapp.model.Resume;

import java.io.*;
import java.nio.file.Path;

public class StreamPathStorage implements Serialization<Path>{
    @Override
    public void doWrite(Resume r, OutputStream os)  {
        try(ObjectOutputStream oos = new ObjectOutputStream(os)){
            oos.writeObject(r);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Resume doRead(InputStream is)  {
        try (ObjectInputStream file = new ObjectInputStream(is)) {
            return (Resume) file.readObject();
        } catch (ClassNotFoundException e) {
            throw new StorageException("Error read", null, e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
