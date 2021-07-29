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
        for (Thread myThread : myThreads) {
            myThread.start();
        }
    }
    public void startSync(){
        counter= counter2 =0;
        for(int i=0; i< myThreads.length; i++){
            myThreads[i] = new MyThread1(numberOfIterations);
        }
        for (Thread myThread : myThreads) {
            myThread.start();
        }
    }


    public static void main(final String[] args) {
        Part3 t = new Part3(2,10);
        t.startAsync();
        try {
            for(Thread thread: t.myThreads){
                thread.join();
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        t.startSync();
        try {
            for(Thread thread: t.myThreads){
                thread.join();
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    public void compare(int numberOfIteration) {
        for (int i =0; i<numberOfIteration; i++) {
            System.out.println(counter + "==" + counter2);
            counter++;
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                return;
            }
            counter2++;
        }
    }

    public synchronized void compareSync() {
        System.out.println(counter+ "==" +counter2);
        counter++;
        try {
             Thread.sleep(100); //NOSONAR
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            return;
        }
        counter2++;
    }

    class MyThread extends Thread{
        private final int numberOfIteration;

        public MyThread(int numberOfIterations){
            this.numberOfIteration = numberOfIterations;
        }

        @Override
        public void run() {
                compare(numberOfIteration);
        }
    }

    class MyThread1 extends Thread{
        private final int numberOfIteration;

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
