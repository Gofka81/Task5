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
        for(int j=0; j< myThreads.length; j++){
            myThreads[j] = new MyThread1(numberOfIterations);
        }
        for (Thread myThread : myThreads) {
            myThread.start();
        }
    }

    public void stop(){
        try {
            for(Thread thread: myThreads){
                thread.join();
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    public static void main(final String[] args) {
        Part3 t = new Part3(2,10);
        t.compare();
        t.compareSync();
    }

    public void compare() {
        startAsync();
        stop();
    }

    public synchronized void compareSync() {
        startSync();
        stop();
    }

    class MyThread extends Thread{
        private final int numberOfIteration;

        public MyThread(int numberOfIterations){
            this.numberOfIteration = numberOfIterations;
        }

        @Override
        public void run() {
            for (int i =0; i<numberOfIteration; i++){
                System.out.println(counter+ "==" +counter2);
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
    }

    class MyThread1 extends Thread{
        private final int numberOfIteration;

        public MyThread1(int numberOfIterations){
            this.numberOfIteration = numberOfIterations;
        }

        @Override
        public void run() {
            for (int j =0; j<numberOfIteration; j++){
                synchronized (myThreads) {
                    System.out.println(counter + "==" + counter2);
                    counter++;
                    try {
                        Thread.sleep(100); //NOSONAR
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();

                    }
                    counter2++;
                }
            }
        }
    }
}
