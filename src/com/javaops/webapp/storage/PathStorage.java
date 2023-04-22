package com.javaops.webapp.storage;

import com.javaops.webapp.exception.StorageException;
import com.javaops.webapp.model.Resume;
import com.javaops.webapp.storage.strategy.ObjectStream;
import com.javaops.webapp.storage.strategy.Serialization;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class PathStorage extends AbstractStorage<Path>{
    private final Path directory;
    private final static Serialization STRATEGY = new ObjectStream();

    public PathStorage(String dir) {
        directory = Paths.get(dir);
        Objects.requireNonNull(directory, "directory must not be null");
        if (!Files.isDirectory(directory) || !Files.isWritable(directory)) {
            throw new IllegalArgumentException(dir + " is not directory or is not writable");
        }
    }

    @Override
    public void clear() {
        try {
            Files.list(directory).forEach(this::doDelete);
        } catch (IOException e) {
            throw new StorageException("Path delete error", null);
        }
    }

    @Override
    public int size() {
        String [] file = directory.toFile().list();
        if (file == null){
            throw new StorageException("Directory can't be null",directory.getParent().toString());
        }
        return file.length;
    }

    @Override
    protected Path getSearchKey(String uuid) {
        return directory.resolve(uuid);
    }

    @Override
    protected void doUpdate(Path path, Resume r) {
        try {
            STRATEGY.doWrite(r, new BufferedOutputStream(Files.newOutputStream(path)));
        } catch (IOException e) {
            throw new StorageException("Path write error", r.getUuid(), e);
        }
    }

    @Override
    protected boolean isExist(Path path) {
        return Files.exists(path);
    }

    @Override
    protected void doSave(Path path, Resume r) {
        try {
            Files.createFile(path);
        } catch (IOException e) {
            throw new StorageException("Couldn't create Path " + path.getRoot(),path.getFileName().toString() , e);
        }
        doUpdate(path, r);
    }

    @Override
    protected Resume doGet(Path path) {
        try {
            return STRATEGY.doRead(new BufferedInputStream(Files.newInputStream(path)));
        } catch (IOException e) {
            throw new StorageException("Path read error", path.getRoot().toString(), e);
        }
    }

    @Override
    protected void doDelete(Path path)  {
        try {
            Files.delete(path);
            if (Files.deleteIfExists(path)) {
                throw new StorageException("Path delete error", path.getParent().toString());
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected List<Resume> doGetAll()  {
        Object[] paths;
        try {
            paths = Files.list(directory).toArray();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        List<Resume> list = new ArrayList<>(paths.length);
        for (Object path : paths) {
            list.add(doGet((Path) path));
        }
        return list;
    }
}
