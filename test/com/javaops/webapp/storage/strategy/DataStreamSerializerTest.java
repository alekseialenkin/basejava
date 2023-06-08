package com.javaops.webapp.storage.strategy;

import com.javaops.webapp.storage.AbstractStorageTest;
import com.javaops.webapp.storage.PathStorage;

import static com.javaops.webapp.TestData.STORAGEDIR;

class DataStreamSerializerTest extends AbstractStorageTest {
    public DataStreamSerializerTest(){
        super(new PathStorage(STORAGEDIR.getAbsolutePath(), new DataStreamSerializer()));
    }
}