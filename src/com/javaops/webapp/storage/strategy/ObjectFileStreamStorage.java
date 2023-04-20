package com.javaops.webapp.storage.strategy;

import com.javaops.webapp.exception.StorageException;
import com.javaops.webapp.model.Resume;
import com.javaops.webapp.storage.AbstractStorage;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ObjectFileStreamStorage extends AbstractStorage<File> implements Serialization {
    private File directory;

    protected ObjectFileStreamStorage(File directory) {
        Objects.requireNonNull(directory, "directory must not be null");
        if (!directory.isDirectory()) {
            throw new IllegalArgumentException(directory.getAbsolutePath() + " is not directory");
        }
        if (!directory.canRead() || !directory.canWrite()) {
            throw new IllegalArgumentException(directory.getAbsolutePath() + " is not readable/writable");
        }
        this.directory = directory;
    }

    @Override
    protected File getSearchKey(String uuid) {
        return new File(directory, uuid);
    }

    @Override
    protected List<Resume> doGetAll() {
        File[] fileList = directory.listFiles();
        if (directory == null) {
            throw new StorageException("Directory can't be null", directory.getName());
        }
        List<Resume> resumeList = new ArrayList<>(fileList.length);
        for (File file : fileList) {
            resumeList.add(doGet(file));
        }
        return resumeList;
    }

    @Override
    protected boolean isExist(File file) {
        return file.exists();
    }

    @Override
    protected void doSave(File file, Resume r) {
        try {
            file.createNewFile();
        } catch (IOException e) {
            throw new StorageException("Couldn't create new file " + file.getAbsolutePath(), file.getName(), e);
        }
        doUpdate(file, r);
    }

    @Override
    protected void doDelete(File file) {
        if (!file.delete()) {
            throw new StorageException("File is not deleted", file.getName());
        }
    }

    @Override
    protected Resume doGet(File file) {
        Resume r;
        try {
            r = doRead(new BufferedInputStream(new FileInputStream(file)));
        } catch (IOException e) {
            throw new StorageException("File read error", file.getName(), e);
        }
        return r;
    }


    @Override
    protected void doUpdate(File file, Resume r) {
        try {
            doWrite(r, new BufferedOutputStream(new FileOutputStream(file)));
        } catch (IOException e) {
            throw new StorageException("File write error", file.getName(), e);
        }
    }

    @Override
    public void clear() {
        File[] files = directory.listFiles();
        if (files == null) {
            throw new StorageException("Directory can't be null", directory.getName());
        } else {
            for (File file : files) {
                doDelete(file);
            }
        }
    }

    @Override
    public int size() {
        String[] listFiles = directory.list();
        if (listFiles == null) {
            throw new StorageException("Directory can't be null", directory.getName());
        }
        return listFiles.length;

    }

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
