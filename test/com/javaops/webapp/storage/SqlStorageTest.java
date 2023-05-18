package com.javaops.webapp.storage;

import com.javaops.webapp.Config;

class SqlStorageTest extends AbstractStorageTest {

    protected SqlStorageTest() {
        super(Config.get().getStorage());
    }
}