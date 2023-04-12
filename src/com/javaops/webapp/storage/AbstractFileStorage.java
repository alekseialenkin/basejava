package com.javaops.webapp.storage;

import com.javaops.webapp.exception.StorageException;
import com.javaops.webapp.model.Resume;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public abstract class AbstractFileStorage extends AbstractStorage<File> {
    private File directory;

    protected AbstractFileStorage(File directory) {
        Objects.requireNonNull(directory, "directory must not be null");
        if (!directory.isDirectory()) {
            throw new IllegalArgumentException(directory.getAbsolutePath() + " is not directory");
        }
        if (!directory.canRead() || !directory.canWrite()){
            throw new IllegalArgumentException(directory.getAbsolutePath() + " is not readable/writable");
        }
        this.directory = directory;
    }

    @Override
    protected File getSearchKey(String uuid) {
        return new File(directory,uuid);
    }

    @Override
    protected List<Resume> doGetAll() {
        File[] fileList = directory.listFiles();
        return doGetAllResumes(fileList);
    }

    protected abstract List<Resume> doGetAllResumes(File[] fileList);

    @Override
    protected boolean isExist(File file) {
        return file.exists();
    }

    @Override
    protected void doSave(File file, Resume r) {
        try {
            file.createNewFile();
            doWrite(r,file);
        } catch (IOException e) {
            throw new StorageException("IO error", file.getName(), e);
        }
    }

    protected abstract void doWrite(Resume r, File file) throws IOException;

    @Override
    protected void doDelete(File file, Resume r) {
        doFileDelete(r,file);
        if (file.delete()){
            System.out.println("File has been deleted");
        }
    }

    protected abstract void doFileDelete(Resume r, File file);

    @Override
    protected Resume doGet(File file, String uuid) {
        return doGetFile(file,uuid);
    }

    protected abstract Resume doGetFile(File file, String uuid);

    @Override
    protected void doUpdate(File file, Resume r) {
        try {
            doWrite(r,file);
        } catch (IOException e) {
            throw new StorageException("IO error", file.getName(), e);
        }
    }

    @Override
    public void clear() {
        List<File> listFiles = Arrays.asList(Objects.requireNonNull(directory.listFiles()));
        listFiles.clear();
        directory = (File) listFiles;
    }

    @Override
    public int size() {
        File[] listFiles = directory.listFiles();
        return Objects.requireNonNull(listFiles).length;
//        return (int) directory.length();
    }
}
