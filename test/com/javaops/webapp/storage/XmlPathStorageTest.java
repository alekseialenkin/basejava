package com.javaops.webapp.storage;

import com.javaops.webapp.storage.strategy.XmlStreamSerializer;

public class XmlPathStorageTest extends AbstractStorageTest{
    public XmlPathStorageTest(){
        super(new PathStorage(STORAGEDIR.getAbsolutePath(),new XmlStreamSerializer()));
    }
}
