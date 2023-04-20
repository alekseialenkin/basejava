package com.javaops.webapp.storage;

import com.javaops.webapp.storage.strategy.ObjectFileStreamStorage;
import com.javaops.webapp.storage.strategy.Strategy;

import java.io.File;

public class ObjectStreamFileStorage {
    private  File directory;
    Strategy st = new Strategy(new ObjectFileStreamStorage(directory));

}
