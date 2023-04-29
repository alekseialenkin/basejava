package com.javaops.webapp.storage.strategy;

import com.javaops.webapp.storage.AbstractStorageTest;
import com.javaops.webapp.storage.PathStorage;

import static org.junit.jupiter.api.Assertions.*;

class DataStreamSerializerTest extends AbstractStorageTest {
    public DataStreamSerializerTest(){
        super(new PathStorage(STORAGEDIR, new DataStreamSerializer()));
    }
}