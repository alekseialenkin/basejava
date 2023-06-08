package com.javaops.webapp.storage;

import com.javaops.webapp.storage.strategy.XmlStreamSerializer;

import static com.javaops.webapp.TestData.STORAGEDIR;

public class XmlPathStorageTest extends AbstractStorageTest{
    public XmlPathStorageTest(){
        super(new PathStorage(STORAGEDIR.getAbsolutePath(),new XmlStreamSerializer()));
    }
}
