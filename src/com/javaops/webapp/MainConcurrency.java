package com.javaops.webapp;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class MainConcurrency {
    public static final int THREAD_NUMBER = 10000;
    private static int counter;
    private final static AtomicInteger atomicCounter = new AtomicInteger();
    private static final String LOCK1 = "LOCK1";
    private static final String LOCK2 = "LOCK2";
    private static final Lock LOCK3 = new ReentrantLock();

    public static void main(String[] args) throws InterruptedException, ExecutionException {
        System.out.println(Thread.currentThread().getName());
        Thread thread0 = new Thread() {
            @Override
            public void run() {
                System.out.println(getName() + ", " + getState());
            }
        };
        thread0.start();
        new Thread(() -> System.out.println(Thread.currentThread().getName() + ", " + Thread.currentThread().getState())).start();
//        List<Thread> threadList = new ArrayList<>(THREAD_NUMBER);
        System.out.println(thread0.getState());
        CountDownLatch latch = new CountDownLatch(THREAD_NUMBER);
        ExecutorService executorService = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
//        CompletionService completionService = new ExecutorCompletionService(executorService);
        for (int i = 0; i < THREAD_NUMBER; i++) {
            Future<Integer> future = executorService.submit(() -> {
//            Thread thread1 = new Thread(() -> {
                for (int j = 0; j < 100; j++) {
                    inc();
                }
                latch.countDown();
                return 5;
            });
//            System.out.println(future.isDone());
//            System.out.println(future.get());
//            thread1.start();
//            threadList.add(thread1);
        }
//        threadList.forEach((t)-> {
//            try {
//                t.join();
//            } catch (InterruptedException e) {
//                throw new RuntimeException(e);
//            }
//        });
        latch.await();
        executorService.shutdown();
        System.out.println(atomicCounter.get());
    }

    private static void inc() {
//        synchronized (LOCK) {
//        LOCK3.lock();
//        try {
            atomicCounter.incrementAndGet();
//            counter++;
//        } finally {
//            LOCK3.unlock();
//        }
    }
}
//DeadLock
//       deadLock(LOCK1,LOCK2);
//       deadLock(LOCK2,LOCK1);
//
//    }
//    private static void deadLock(Object LOCK1, Object LOCK2){
//       Thread thread =  new Thread(()->{
//            System.out.println( Thread.currentThread().getName() + " : Пытаемся захватить " + LOCK1 );
//            synchronized (LOCK1){
//                System.out.println(Thread.currentThread().getName() + " : " +  LOCK1 + " захвачен ");
//                System.out.println(Thread.currentThread().getName() + " : Пытаемся захватить " + LOCK2);
//                synchronized (LOCK2){
//                    System.out.println("LOCK1 и LOCK2 захвачены");
//                }
//            }
//        });
//       thread.start();
//    }

//}
