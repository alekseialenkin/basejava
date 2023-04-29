package com.javaops.webapp.storage;

import com.javaops.webapp.storage.strategy.ObjectStream;

import java.io.File;

class FileStorageTest extends AbstractStorageTest{
        public FileStorageTest(){
            super(new FileStorage(new File(STORAGEDIR),new ObjectStream()));
        }
}