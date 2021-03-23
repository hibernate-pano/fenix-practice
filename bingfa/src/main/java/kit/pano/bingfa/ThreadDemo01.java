package kit.pano.bingfa;

import java.util.concurrent.locks.ReentrantLock;

/**
 * 并发售票问题
 */
public class ThreadDemo01 implements Runnable {

    private int tickets = 100;

    @Override
    public void run() {
        while (tickets > 0) {
            sellTickets();
        }
    }

//    //此处增加synchronized关键字，保证线程安全
//    //但是通过加锁的方式实现线程安全的方式会导致性能降低
//    private synchronized void sellTickets() {
//        if (tickets > 0) {
//            System.out.println(Thread.currentThread().getName() + tickets);
//            tickets--;
//        }
//    }

    //使用ReentrantLock加锁
    ReentrantLock lock = new ReentrantLock();
    private void sellTickets() {
        lock.lock();
        if (tickets > 0) {
            System.out.println(Thread.currentThread().getName() + tickets);
            tickets--;
        }
        lock.unlock();
    }

    public static void main(String[] args) {
        ThreadDemo01 t = new ThreadDemo01();
        Thread t1 = new Thread(t);
        Thread t2 = new Thread(t);
        t1.setName("t1 售票站台");
        t2.setName("t2 售票站台");
        t1.start();
        t2.start();
    }

}
