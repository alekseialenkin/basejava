package com.javaops.webapp.storage;

import com.javaops.webapp.storage.strategy.ObjectStream;

import static com.javaops.webapp.TestData.STORAGEDIR;

class PathStorageTest extends AbstractStorageTest{
    public PathStorageTest(){
        super(new PathStorage(STORAGEDIR.getAbsolutePath(),new ObjectStream()));
    }
}