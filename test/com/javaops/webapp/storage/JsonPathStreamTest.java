package com.javaops.webapp.storage;

import com.javaops.webapp.storage.strategy.JsonStreamSerializer;

public class JsonPathStreamTest extends AbstractStorageTest{
    public JsonPathStreamTest(){
        super(new PathStorage(STORAGEDIR.getAbsolutePath(),new JsonStreamSerializer()));
    }
}