package kit.pano.bingfa;

import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 此版利用condition完成自定义blockingQueue
 */
public class MyBlockingQueue01 {

    private Queue<Integer> queue;
    private int max;
    private ReentrantLock lock = new ReentrantLock();
    private Condition notEmpty = lock.newCondition();
    private Condition notFull = lock.newCondition();

    public MyBlockingQueue01(int max) {
        this.max = max;
        queue = new LinkedList<>();
    }

    public void put(int i) throws InterruptedException {
        //先锁定
        lock.lock();

        try {
            //但队列满时，生产者停止生产
            while (queue.size() == max) {
                notFull.await();
            }
            //
            queue.add(i);
            //唤醒所有消费者
            notEmpty.signalAll();
        } finally {
            lock.unlock();
        }
    }

    public Integer take() throws InterruptedException {
        //先锁定
        lock.lock();
        try {
            //但队列满时，消费者停止消费
            while (queue.size() == 0) {
                notEmpty.await();
            }
            //
            Integer item = queue.remove();
            //唤醒所有生产者
            notFull.signalAll();
            return item;
        } finally {
            lock.unlock();
        }
    }
}
