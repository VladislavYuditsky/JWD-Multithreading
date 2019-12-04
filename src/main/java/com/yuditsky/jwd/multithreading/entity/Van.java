package com.yuditsky.jwd.multithreading.entity;

import java.util.Random;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class Van implements Runnable{
    private Base base;
    private Cargo cargo;
    private int number;

    private ReentrantLock lock;
    private Condition condition;

    public Van(Base base, Cargo cargo, int number) {
        this.base = base;
        this.cargo = cargo;
        this.number = number;
        lock = new ReentrantLock();
        condition = lock.newCondition();
    }

    public ReentrantLock getLock() {
        return lock;
    }

    public Condition getCondition() {
        return condition;
    }

    public Cargo getCargo() {
        return cargo;
    }

    public void setCargo(Cargo cargo) {
        this.cargo = cargo;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    @Override
    public void run() {
        Random random = new Random();
        /*try {
            TimeUnit.SECONDS.sleep(random.nextInt(4) + 1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }*/

        System.out.println("Фургон с номером: " + number + " прибыл");
        base.serve(this);

        lock.lock();
        try {
            System.out.println("жду");
            condition.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        lock.unlock();

        System.out.println("Уехал " + number);
    }

    @Override
    public String toString() {
        return "Van{" +
                "base=" + base +
                ", cargo=" + cargo +
                ", number=" + number +
                ", lock=" + lock +
                ", condition=" + condition +
                '}';
    }
}
