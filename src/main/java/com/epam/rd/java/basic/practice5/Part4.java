package com.epam.rd.java.basic.practice5;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

import static java.lang.Thread.sleep;

public class Part4 {

    private static int max1;
    private static int max2;

    private final Matrix matrix;
    private final Thread[] threads;

    public static void main(final String[] args) {
        Part4 t = new Part4();
        long time1 = System.nanoTime();
        t.startSync();
        try {
            t.threads[0].join();
        } catch (InterruptedException e) {
            return;
        }
        System.out.println(max1);
        System.out.println((System.nanoTime() - time1)/1000000);
        long time2 = System.nanoTime();
        t.startSimple();
        System.out.println(max2);
        System.out.println((System.nanoTime() - time2)/1000000);
    }



    public Part4(){
        matrix = new Matrix();
        threads = new Thread[Matrix.length];

        for(int i=0; i< Matrix.length; i++){
            threads[i] = new Thread(new MyThread());
        }
    }

    public void startSync(){
        for (Thread thread: threads ){
            thread.start();
        }
    }

    public void startSimple(){
        Matrix.counter=0;
        for (int i =0; i<Matrix.length; i++){
            for (int current: Matrix.read()){
                if(current > max2){
                    max2 = current;
                }
                try {
                    sleep(1);
                } catch (InterruptedException e) {
                    System.out.println("Fuck!");
                    return;
                }
            }
        }
    }

    class MyThread implements Runnable{

        @Override
        public void run() {
            int max;
            int[] temp=Matrix.read();
            if(temp.length <=0){
                return;
            }
            max = temp[0];
            for(int i: temp){
                if(i> max){
                    max= i;
                }
                try {
                    sleep(1);
                } catch (InterruptedException e) {
                    System.out.println("Fuck!");
                    return;
                }
            }
            synchronized (this) {
                if (max > max1) {
                    max1 = max;
                }
            }
        }
    }

    static class Matrix {

        private static String[] strings;
        private static int counter;
        private static int length;

        public Matrix(){
            strings = getInput("part4.txt");
            length = strings.length;
            counter =0;
        }

        public static int[] read(){
            counter++;
            return Arrays.asList(strings[counter-1].trim().split(" ")).stream().mapToInt(Integer::parseInt).toArray();
        }

        private static String[] getInput(String fileName) {
            Logger logger = Logger.getAnonymousLogger();
            StringBuilder sb = new StringBuilder();
            try {
                Scanner scanner = new Scanner(new File(fileName), "UTF-8");
                while (scanner.hasNextLine()) {
                    sb.append(scanner.nextLine()).append(System.lineSeparator());
                }
                scanner.close();
                return sb.toString().trim().split(System.lineSeparator());
            } catch (IOException ex) {
                logger.log(Level.SEVERE, "IOException");
            }
            return sb.toString().split(System.lineSeparator());
        }
    }
}


