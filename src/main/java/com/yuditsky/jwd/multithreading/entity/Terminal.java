package com.yuditsky.jwd.multithreading.entity;

import org.w3c.dom.ls.LSOutput;

import java.util.Random;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class Terminal implements Runnable {
    private boolean free;
    private Van van;

    ReentrantLock lock = new ReentrantLock();

    public Terminal() {
        free = true;
    }

    public boolean isFree() {
        return free;
    }

    public void setFree(boolean free) {
        this.free = free;
    }

    public Van getVan() {
        return van;
    }

    public void setVan(Van van) {
        //lock.lock();
        this.van = van;
        System.out.println(van);
        //lock.unlock();
    }

    @Override
    public void run() {
        System.out.println("Терминал");
        while (true) {
            //System.out.println(van);
            if (van != null) {
                free = false;
                ReentrantLock lock = van.getLock();
                Condition condition = van.getCondition();

                System.out.println("Работа с фургоном: " + van.getNumber() + " началась");
                /*try {
                    TimeUnit.SECONDS.sleep(5);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }*/

                lock.lock();
                Cargo cargo = van.getCargo();
                if (cargo.equals(Cargo.EMPTY)) {
                    Random random = new Random();
                    if (random.nextInt() % 2 == 0){
                        van.setCargo(Cargo.PERISHABLE);
                        System.out.println("Загружен скоропортящимися продуктами " + van.getNumber());
                    } else {
                        van.setCargo(Cargo.NOT_PERISHABLE);
                        System.out.println("Загружен не скоропортящимися продуктами " + van.getNumber());
                    }
                } else {
                    van.setCargo(Cargo.EMPTY);
                    System.out.println("Разгружен " + van.getNumber());
                }

                condition.signal();
                lock.unlock();
                van = null;
                free = true;
            }
        }
    }

    @Override
    public String toString() {
        return "Terminal{" +
                "free=" + free +
                ", van=" + van +
                '}';
    }
}
