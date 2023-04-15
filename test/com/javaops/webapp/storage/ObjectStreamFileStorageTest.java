package com.javaops.webapp.storage;

import java.io.File;

class ObjectStreamFileStorageTest extends AbstractStorageTest{
        public ObjectStreamFileStorageTest(){
            super(new ObjectStreamFileStorage(new File(STORAGEDIR)));
        }
}