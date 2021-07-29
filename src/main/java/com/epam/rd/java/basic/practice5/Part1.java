package com.epam.rd.java.basic.practice5;

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
            e.printStackTrace();
        }
    }
}

class MyThreadC extends Thread{
    @Override
    public void run() {
        for(int i = 0; i<4; ++i){
            System.out.println(getName());
            try {
                sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}

class MyThreadI implements Runnable{

    @Override
    public void run() {
        for(int i = 0; i<4; ++i){
            System.out.println(Thread.currentThread().getName());
            try{
                Thread.sleep(500);
            }catch (InterruptedException e){
                e.printStackTrace();
            }
        }
    }
}