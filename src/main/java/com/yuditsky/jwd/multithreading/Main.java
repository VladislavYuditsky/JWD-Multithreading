package com.yuditsky.jwd.multithreading;

import com.yuditsky.jwd.multithreading.entity.Base;
import com.yuditsky.jwd.multithreading.entity.Cargo;
import com.yuditsky.jwd.multithreading.entity.Van;

public class Main {
    public static void main(String[] args) {
        Base base =  new Base();
        Thread baseThread = new Thread(base);
        baseThread.start();

        Van van = new Van(base, Cargo.PERISHABLE, 1);
        Thread vanThread = new Thread(van);
        vanThread.start();

        /*for(int i = 0; i < 15; i++) {
            Van van = new Van(base, Cargo.PERISHABLE, i);
            Thread vanThread = new Thread(van);
            vanThread.start();
        }*/
    }
}
