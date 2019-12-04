package com.yuditsky.jwd.multithreading;

import com.yuditsky.jwd.multithreading.entity.Base;
import com.yuditsky.jwd.multithreading.entity.Cargo;
import com.yuditsky.jwd.multithreading.entity.Van;

public class Main {
    public static void main(String[] args) {
        Base base = Base.getInstance();
        Thread baseThread = new Thread(base);

        baseThread.start();

        for (int i = 0; i < 5; i++) {
            Van van = new Van(base, Cargo.NOT_PERISHABLE, i);
            Thread vanThread = new Thread(van);
            vanThread.start();
        }

        for (int i = 5; i < 10; i++) {
            Van van = new Van(base, Cargo.EMPTY, i);
            Thread vanThread = new Thread(van);
            vanThread.start();
        }

        for (int i = 10; i < 15; i++) {
            Van van = new Van(base, Cargo.PERISHABLE, i);
            Thread vanThread = new Thread(van);
            vanThread.start();
        }

    }
}
