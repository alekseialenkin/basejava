package com.javaops.webapp.storage;

import com.javaops.webapp.storage.strategy.ObjectStream;

class FileStorageTest extends AbstractStorageTest{
        public FileStorageTest(){
            super(new FileStorage(STORAGEDIR,new ObjectStream()));
        }
}