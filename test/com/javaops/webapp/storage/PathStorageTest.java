package com.javaops.webapp.storage;

class PathStorageTest extends AbstractStorageTest{
    public PathStorageTest(){
        super(new PathStorage(STORAGEDIR));
    }
}