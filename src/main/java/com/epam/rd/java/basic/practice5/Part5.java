package com.epam.rd.java.basic.practice5;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.charset.StandardCharsets;

public class Part5 {

    private File f;
    private RandomAccessFile raf;
    private PrintThread[] threads;

    public static void main(final String[] args) {
        Part5 t = new Part5();
        t.createFile();
        try {
            t.raf = new RandomAccessFile("part5.txt", "rw");

        } catch (IOException e) {
            System.out.println("oops");
        }
        t.createThreads(10);
        t.start();
        try {
            t.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(Thread.activeCount());
    }

    public void createThreads(int num){
        threads = new PrintThread[num];
        for(int i =0; i<num; i++){
            threads[i] = new PrintThread(i);
        }
    }

    public void start(){
        for(int i=0; i< threads.length; i++){
            threads[i].start();
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
        f = new File("part5.txt");
        if(f.exists()) {
            f.delete();
        }
        try {
            f.createNewFile();

        } catch (IOException e) {
            System.out.println("oops");
        }
    }

    class PrintThread extends Thread {
        private int num;

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
