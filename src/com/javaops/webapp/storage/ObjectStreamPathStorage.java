package com.javaops.webapp.storage;

import com.javaops.webapp.storage.strategy.ObjectPathStreamStorage;
import com.javaops.webapp.storage.strategy.Strategy;

public class ObjectStreamPathStorage {
    private String directory;
    Strategy st = new Strategy(new ObjectPathStreamStorage(directory));
}
