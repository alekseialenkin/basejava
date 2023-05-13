package com.javaops.webapp;

public class MainConcurrency {
    public static final int THREAD_NUMBER = 10000;
    private static int counter;
    private static final Object LOCK1 = new Object();
    private static final Object LOCK2 = new Object();

    public static void main(String[] args) throws InterruptedException {
        System.out.println(Thread.currentThread().getName());
        Thread thread = new Thread(() -> {
//                System.out.println(getName() + ", " + getState());
            System.out.println("thread: Пытаемся захватить LOCK1");
            synchronized (LOCK1){
                System.out.println("thread: LOCK1 захвачен");
                System.out.println("thread: Пытаемся захватить LOCK2");
                synchronized (LOCK2){
                    System.out.println("thread: LOCK1 и LOCK2 захвачены");
                }
            }
        });
        Thread thread1 = new Thread(() -> {
            System.out.println("thread1: Пытаемся захватить LOCK2");
            synchronized (LOCK2){
                System.out.println("thread1: LOCK2 захвачен");
                System.out.println("thread1: Пытаемся захватить LOCK1");
                synchronized (LOCK1){
                    System.out.println("thread1: LOCK1 и LOCK2 захвачены");
                }
            }
        });
        thread.start();
        thread1.start();
//        new Thread(() -> System.out.println(Thread.currentThread().getName() + ", " + Thread.currentThread().getState())).start();
//        List<Thread> threadList = new ArrayList<>(THREAD_NUMBER);
//        System.out.println(thread.getState());
//        for (int i = 0; i < THREAD_NUMBER; i++) {
//            Thread thread1 = new Thread(() -> {
//                for (int j = 0; j < 100; j++) {
//                    inc();
//                }
//            });
//            thread1.start();
//            threadList.add(thread1);
//        }
//        threadList.forEach((t)-> {
//            try {
//                t.join();
//            } catch (InterruptedException e) {
//                throw new RuntimeException(e);
//            }
//        });
//        System.out.println(counter);
//    }
//
//    private synchronized static void inc() {
////        synchronized (LOCK) {
//            counter++;
//        }
    }
    //DeadLock

}
