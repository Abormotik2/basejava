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
        Thread1 thread1 = new Thread1();
        Thread2 thread2 = new Thread2();
        thread1.resource1 = resource1;
        thread2.resource2 = resource2;
        thread1.start();
        thread2.start();
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
    static class Thread1 extends Thread {
        Resource1 resource1;

        @Override
        public void run() {
            System.out.println(resource1.deadLine());
        }
    }

    static class Thread2 extends Thread {
        Resource2 resource2;

        @Override
        public void run() {
            System.out.println(resource2.getI());
        }
    }

    static class Resource1 {
        Resource2 resource2;

        public synchronized int deadLine() {
            return resource2.returnI();
        }

        public synchronized int returnI() {
            return 1;
        }
    }

    static class Resource2 {
        Resource1 resource1;

        public synchronized int getI() {
            return resource1.returnI();
        }

        public synchronized int returnI() {
            return 2;
        }
    }
}