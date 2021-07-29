package com.epam.rd.java.basic.practice5;

public class Part3 {

    private int counter;
    private int counter2;

    private final Thread[] myThreads;
    private final int numberOfIterations;

    public Part3(int numberOfThreads, int numberOfIterations) {
        myThreads = new Thread[numberOfThreads];
        this.numberOfIterations = numberOfIterations;
    }

    public void startAsync(){
        for(int i=0; i< myThreads.length; i++){
            myThreads[i] = new MyThread(numberOfIterations);
        }
        for(int i =0; i< myThreads.length; i++){
            myThreads[i].start();
        }
    }
    public void startSync(){
        counter= counter2 =0;
        for(int i=0; i< myThreads.length; i++){
            myThreads[i] = new MyThread1(numberOfIterations);
        }
        for(int i =0; i< myThreads.length; i++){
            myThreads[i].start();
        }
    }


    public static void main(final String[] args) {
        Part3 t = new Part3(10,3);
        t.startAsync();
        try {
            for(Thread thread: t.myThreads){
                thread.join();
            }
        } catch (InterruptedException e) {
            return;
        }
        t.startSync();
        try {
            for(Thread thread: t.myThreads){
                thread.join();
            }
        } catch (InterruptedException e) {
            return;
        }
    }

    public void compare() {
        System.out.println(counter == counter2);
        counter++;
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            return;
        }
        counter2++;
    }

    public void compareSync() {
        synchronized (this){
            System.out.println(counter == counter2);
            counter++;
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                return;
            }
            counter2++;
        }
    }

    class MyThread extends Thread{
        private int numberOfIteration;

        public MyThread(int numberOfIterations){
            this.numberOfIteration = numberOfIterations;
        }

        @Override
        public void run() {
            for (int i =0; i<numberOfIteration; i++){
                compare();

            }
        }
    }

    class MyThread1 extends Thread{
        private int numberOfIteration;

        public MyThread1(int numberOfIterations){
            this.numberOfIteration = numberOfIterations;
        }

        @Override
        public void run() {
            for (int i =0; i<numberOfIteration; i++){
                compareSync();

            }
        }
    }
}
