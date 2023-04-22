package com.javaops.webapp.storage;

import java.io.File;

class FileStorageTest extends AbstractStorageTest{
        public FileStorageTest(){
            super(new FileStorage(new File(STORAGEDIR)));
        }
}