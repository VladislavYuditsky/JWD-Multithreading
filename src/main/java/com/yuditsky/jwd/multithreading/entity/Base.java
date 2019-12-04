package com.yuditsky.jwd.multithreading.entity;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.locks.ReentrantLock;

public class Base implements Runnable {
    private static final int TERMINAL_NUMBER = 2;

    private List<Terminal> terminals;
    private Queue<Van> vans;
    private Queue<Van> perishableCargoVans;

    private ReentrantLock lock = new ReentrantLock();

    public Base() {
        terminals = new ArrayList<Terminal>();
        for (int i = 0; i < TERMINAL_NUMBER; i++) {
            Terminal terminal = new Terminal();
            terminals.add(terminal);
            Thread ter = new Thread(terminal);
            ter.start();
        }

        vans = new ArrayDeque<Van>();
        perishableCargoVans = new ArrayDeque<Van>();
        lock = new ReentrantLock();
    }

    public void serve(Van van) {
        addToQueue(van);
    }

    private void addToQueue(Van van) {
        lock.lock();

        Cargo cargo = van.getCargo();
        if (cargo.equals(Cargo.PERISHABLE)) {
            perishableCargoVans.add(van);
        } else {
            vans.add(van);
        }

        lock.unlock();
    }

    @Override
    public void run() {
        System.out.println("Base");
        while (true) {
            for (Terminal terminal : terminals) {
                if (terminal.isFree()) {
                    if (!perishableCargoVans.isEmpty()) {
                        System.out.println("E");
                        System.out.println(terminals);
                        terminal.setVan(perishableCargoVans.poll());
                    } else {
                        if (!vans.isEmpty()) {
                            terminal.setVan(vans.poll());
                        }
                    }
                }
            }
        }
    }
}
