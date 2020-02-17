package com.urise.webapp;

import java.util.ArrayList;
import java.util.List;

public class MainConcurrency {
    public static final int THREADS_NUMBER = 10000;
    private volatile int counter;
    private static final Object LOCK = new Object();

    public static void main(String[] args) {
        System.out.println(Thread.currentThread().getName());

        Thread thread0 = new Thread() {
            @Override
            public void run() {
                System.out.println(getName() + ", " + getState());
                throw new IllegalStateException();
            }
        };
        thread0.start();

        new Thread(new Runnable() {

            @Override
            public void run() {
                System.out.println(Thread.currentThread().getName() + ", " + Thread.currentThread().getState());
            }

            private void inc() {
                synchronized (this) {
//                    counter++;
                }
            }

        }).start();

        System.out.println(thread0.getState());

        final MainConcurrency mainConcurrency = new MainConcurrency();
        List<Thread> threads = new ArrayList<>(THREADS_NUMBER);

        for (int i = 0; i < THREADS_NUMBER; i++) {
            Thread thread = new Thread(() -> {
                for (int j = 0; j < 100; j++) {
                    mainConcurrency.inc();
                }
            });
            thread.start();
            threads.add(thread);
        }

        threads.forEach(t -> {
            try {
                t.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        System.out.println(mainConcurrency.counter);

        final Resource1 resource1 = new Resource1();
        final Resource2 resource2 = new Resource2();
        resource1.resource2 = resource2;
        resource2.resource1 = resource1;
        final Thread1 thread1 = new Thread1();
        final Thread2 thread2 = new Thread2();
        thread1.resource1 = resource1;
        thread2.resource2 = resource2;
        thread1.start();
        thread2.start();
        final Object dLock1 = new Object();
        final Object dLock2 = new Object();
        deadLock(dLock1, dLock2);
        deadLock(dLock2, dLock2);
    }

    private void inc() {
//        synchronized (this) {
//        synchronized (MainConcurrency.class) {
        counter++;
//                wait();
//                readFile
//                ...
//        }
    }

    private static class Thread1 extends Thread {
        Resource1 resource1;

        @Override
        public void run() {
            System.out.println(resource1.getI());
        }
    }

    private static class Thread2 extends Thread {
        Resource2 resource2;

        @Override
        public void run() {
            System.out.println(resource2.getI());
        }
    }

    private static class Resource1 {
        Resource2 resource2;

        public synchronized int getI() {
            return resource2.returnI();
        }

        public synchronized int returnI() {
            return 2;
        }
    }

    private static class Resource2 {
        Resource1 resource1;

        public synchronized int getI() {
            return resource1.returnI();
        }

        public synchronized int returnI() {
            return 2;
        }
    }
    private static void deadLock(Object dLock1, Object dLock2) {
        new Thread(() -> {
            synchronized (dLock1) {
                System.out.println(dLock1);
                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    throw new IllegalStateException(e);
                }
                synchronized (dLock2) {
                    System.out.println(dLock2);
                }
            }
        }).start();
    }
}