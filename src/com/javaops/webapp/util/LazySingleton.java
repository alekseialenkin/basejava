package com.javaops.webapp.util;

public class LazySingleton {
    volatile private static LazySingleton INSTANCE;

    private LazySingleton() {
    }
    private static class LazySingletonHandler{
        private static final LazySingleton INSTANCE = new LazySingleton();
    }
    public static LazySingleton getInstance(){
        return LazySingletonHandler.INSTANCE;
    }

}
