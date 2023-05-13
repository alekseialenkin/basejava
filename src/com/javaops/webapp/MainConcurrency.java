package com.javaops.webapp;

public class MainConcurrency {
    public static final int THREAD_NUMBER = 10000;
    private static int counter;
    private static final String LOCK1 = "LOCK1";
    private static final String LOCK2 = "LOCK2";

    public static void main(String[] args) throws InterruptedException {
//        System.out.println(Thread.currentThread().getName());
//        Thread thread0 = new Thread() {
//            @Override
//            public void run() {
//                System.out.println(getName() + ", " + getState());
//            }
//        };
//        thread0.start();
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
//    }
        //DeadLock
       deadLock(LOCK1,LOCK2);
       deadLock(LOCK2,LOCK1);

    }
    private static void deadLock(Object LOCK1, Object LOCK2){
       Thread thread =  new Thread(()->{
            System.out.println( Thread.currentThread().getName() + " : Пытаемся захватить " + LOCK1 );
            synchronized (LOCK1){
                System.out.println(Thread.currentThread().getName() + " : " +  LOCK1 + " захвачен ");
                System.out.println(Thread.currentThread().getName() + " : Пытаемся захватить " + LOCK2);
                synchronized (LOCK2){
                    System.out.println("LOCK1 и LOCK2 захвачены");
                }
            }
        });
       thread.start();
    }

}
