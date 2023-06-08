package com.javaops.webapp.storage;

import com.javaops.webapp.storage.strategy.ObjectStream;

import static com.javaops.webapp.TestData.STORAGEDIR;

class FileStorageTest extends AbstractStorageTest{
        public FileStorageTest(){
            super(new FileStorage(STORAGEDIR,new ObjectStream()));
        }
}