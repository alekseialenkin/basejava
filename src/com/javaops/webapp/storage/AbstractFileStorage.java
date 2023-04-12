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
        return doGetAllResumes(fileList);
    }

    @Override
    protected boolean isExist(File file) {
        return file.exists();
    }

    @Override
    protected void doSave(File file, Resume r) {
        try {
            file.createNewFile();
            doWrite(r, file);
        } catch (IOException e) {
            throw new StorageException("IO error", file.getName(), e);
        }
    }

    @Override
    protected void doDelete(File file, Resume r) {
        if (!file.delete()) {
            throw new StorageException("File is not deleted", file.getName());
        } else {
            System.out.println("File " + file.getName() + " has been deleted");
        }
    }

    @Override
    protected Resume doGet(File file, String uuid)  {
        Resume r;
        try {
            r = doRead(file,uuid);
        } catch (IOException e) {
            throw new StorageException("IO error", file.getName(),e);
        }
        return r;
    }


    @Override
    protected void doUpdate(File file, Resume r) {
        try {
            doWrite(r, file);
        } catch (IOException e) {
            throw new StorageException("IO error", file.getName(), e);
        }
    }

    @Override
    public void clear() {
        List<File> listFiles;
        try {
            listFiles = Arrays.asList(Objects.requireNonNull(directory.listFiles()));
        }catch (NullPointerException e){
            throw new StorageException("Directory can't be null",directory.getName(),e);
        }
        listFiles.clear();
        directory = (File) listFiles;
    }

    @Override
    public int size() {
        File[] listFiles;
        try {
           listFiles = Objects.requireNonNull(directory.listFiles());
        }catch (NullPointerException e ){
            throw new StorageException("Directory can't be null", directory.getName(),e);
        }
        return listFiles.length;
//        return (int) directory.length();
    }

    protected abstract List<Resume> doGetAllResumes(File[] fileList);

    protected abstract Resume doRead(File file, String uuid) throws IOException;

    protected abstract void doWrite(Resume r, File file) throws IOException;
}
