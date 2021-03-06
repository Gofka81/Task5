package com.epam.rd.java.basic.practice5;

import java.io.InputStream;
import java.nio.charset.StandardCharsets;

public class Part2 {

    public static void main(final String[] args) {
        System.setIn(new InputStream()  {
            private  boolean check = false;
            @Override
            public int read(){
                int i = -1;
                if(!check)
                    try {
                        Thread.sleep(2000);
                        check = true;
                        i = "\n".getBytes(StandardCharsets.UTF_8)[0];
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    }
                return i;
            }
        });


        Thread t = new Thread(() -> Spam.main(null));
        t.start();
        try {
            t.join();
        } catch (InterruptedException e) {
            t.interrupt();
        }

        System.setIn(System.in);
    }

}
