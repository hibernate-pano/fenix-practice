package com.example.mistakes.demos;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class SynchronizedDemo {

    volatile int a = 1;
    volatile int b = 1;

    public static void main(String[] args) {
        SynchronizedDemo synchronizedDemo = new SynchronizedDemo();
        new Thread(synchronizedDemo::add).start();
        new Thread(synchronizedDemo::compare).start();
    }

    public synchronized void add() {
        log.info("add start");
        for (int i = 0; i < 100000; i++) {
            a++;
            b++;
        }
        log.info("add done");
    }

    public synchronized void compare() {
        log.info("compare start");
        for (int i = 0; i < 100000; i++) {
            //a始终等于b吗？
            if (a < b) {
                log.info("a:{},b:{},{}", a, b, a > b);
                //最后的a>b应该始终是false吗？
            }
        }
        log.info("compare done");
    }
}