package com.epam.rd.java.basic.practice5;

import java.util.logging.Level;
import java.util.logging.Logger;

public class Part1 {

    public static void main(String[] args) {

        Thread t = new MyThreadC();
        Thread r = new Thread(new MyThreadI());

        try{
            t.start();
            t.join();
            r.start();
            r.join();
        }catch(InterruptedException e){
            Thread.currentThread().interrupt();
        }
    }
}

class MyThreadC extends Thread{
    @Override
    public void run() {
        Logger logger = Logger.getAnonymousLogger();
        for(int i = 0; i<4; ++i){
            System.out.println(getName());
            try {
                sleep(500);
            } catch (InterruptedException e) {
                logger.log(Level.SEVERE, "IOException");
                Thread.currentThread().interrupt();
            }
        }
    }
}

class MyThreadI implements Runnable{

    @Override
    public void run() {
        Logger logger = Logger.getAnonymousLogger();
        for(int i = 0; i<4; ++i){
            System.out.println(Thread.currentThread().getName());
            try{
                Thread.sleep(500);
            }catch (InterruptedException e){
                logger.log(Level.SEVERE, "IOException");
                Thread.currentThread().interrupt();
            }
        }
    }
}