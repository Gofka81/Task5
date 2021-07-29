package com.epam.rd.java.basic.practice5;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;


public class Part5 {

    private File f;
    private RandomAccessFile raf;
    private PrintThread[] threads;


    public static void main(final String[] args) {
        Logger logger = Logger.getAnonymousLogger();
        Part5 t = new Part5();
        t.createFile();
        try {
            t.raf = new RandomAccessFile("part5.txt", "rw");

        } catch (IOException e) {
            logger.log(Level.SEVERE, "IOException1");
        }
        t.createThreads(10);
        t.start();
        try {
            t.join();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        System.out.println(t.printFile());
    }

    public String printFile(){
        Logger logger = Logger.getAnonymousLogger();
        StringBuilder sb = new StringBuilder();
        try {
            Scanner scanner = new Scanner(f, "UTF-8");
            while (scanner.hasNextLine()) {
                sb.append(scanner.nextLine()).append(System.lineSeparator());
            }
            scanner.close();
        } catch (IOException ex) {
            logger.log(Level.SEVERE, "IOException2");
        }
        return sb.toString().trim();
    }

    public void createThreads(int num){
        threads = new PrintThread[num];
        for(int i =0; i<num; i++){
            threads[i] = new PrintThread(i);
        }
    }

    public void start(){
        for (PrintThread thread : threads) {
            thread.start();
        }
    }

    public void join() throws InterruptedException {
        for(PrintThread thread: threads){
            thread.join();
        }
    }

    public synchronized void write(int num,int iteration){
        try {
            raf.seek(iteration+ (long) num *(20+System.lineSeparator().length()));
            raf.write('0'+num);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public synchronized void separator(int num){
        try {
            raf.seek(20 + (long) num *(20+System.lineSeparator().length()));
            raf.write(System.lineSeparator().getBytes(StandardCharsets.UTF_8));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void createFile(){
        Logger logger = Logger.getAnonymousLogger();
        f = new File("part5.txt");
        if(f.exists()) {
            f.delete(); //NOSONAR
        }
        try {
            if(!f.createNewFile()) throw new IOException();

        } catch (IOException e) {
            logger.log(Level.SEVERE, "IOException3");
        }
    }

    class PrintThread extends Thread {
        private final int num;

        public PrintThread(int num){
            this.num = num;
        }
        @Override
        public void run() {
            for(int i=0; i<20; i++){
                write(num,i);
            }
            separator(num);
        }
    }
}
