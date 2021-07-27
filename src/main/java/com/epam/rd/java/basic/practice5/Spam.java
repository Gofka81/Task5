package com.epam.rd.java.basic.practice5;


import java.util.Scanner;

public class Spam {

    private Thread[] threads;

    public Spam(final String[] messages, final int[] delays) {
        threads = new Thread[messages.length];
        for(int i =0; i< threads.length; i++){
            threads[i] = new Worker(messages[i], delays[i]);
        }
    }

    public static void main(final String[] args) {
        Spam spam = new Spam(new String[]{"Dat","why","how","4"}, new int[]{1000,333,2000,500});
        spam.start();
        Scanner sc = new Scanner(System.in);
        String string = sc.nextLine();
        while (string != null) {
            if (string.isEmpty()) {
                spam.stop();
                break;
            }
            if (sc.hasNextLine()) {
                string = sc.nextLine();
            } else {
                string = null;
            }
        }
        sc.close();
    }

    public void start() {
        for (Thread t : threads) {
            t.start();
        }
    }

    public void stop() {
        for(int i =0; i< threads.length; i++) {
            threads[i].stop();
        }
    }

    private static class Worker extends Thread {
        private int delay;
        private String message;

        public Worker(String message, int delay){
            this.message = message;
            this.delay = delay;
        }

        @Override
        public void run() {
            while (true) {
                System.out.println(message);
                try {
                    sleep(delay);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

}
