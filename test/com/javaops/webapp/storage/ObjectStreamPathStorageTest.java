package com.javaops.webapp.storage;

import java.io.File;

class ObjectStreamPathStorageTest extends AbstractStorageTest{
    public ObjectStreamPathStorageTest(){
        super(new ObjectStreamFileStorage(new File(STORAGEDIR)));
    }
}