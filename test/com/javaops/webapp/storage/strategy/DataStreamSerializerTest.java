package com.javaops.webapp.storage.strategy;

import com.javaops.webapp.storage.AbstractStorageTest;
import com.javaops.webapp.storage.PathStorage;

class DataStreamSerializerTest extends AbstractStorageTest {
    public DataStreamSerializerTest(){
        super(new PathStorage(STORAGEDIR.getAbsolutePath(), new DataStreamSerializer()));
    }
}