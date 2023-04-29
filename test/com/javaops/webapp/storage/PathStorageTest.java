package com.javaops.webapp.storage;

import com.javaops.webapp.storage.strategy.ObjectStream;

class PathStorageTest extends AbstractStorageTest{
    public PathStorageTest(){
        super(new PathStorage(STORAGEDIR,new ObjectStream()));
    }
}