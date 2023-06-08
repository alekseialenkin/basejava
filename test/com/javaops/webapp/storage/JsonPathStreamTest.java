package com.javaops.webapp.storage;

import com.javaops.webapp.storage.strategy.JsonStreamSerializer;

import static com.javaops.webapp.TestData.STORAGEDIR;

public class JsonPathStreamTest extends AbstractStorageTest{
    public JsonPathStreamTest(){
        super(new PathStorage(STORAGEDIR.getAbsolutePath(),new JsonStreamSerializer()));
    }
}