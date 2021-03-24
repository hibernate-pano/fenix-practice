package kit.pano.bingfa;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

/**
 * 此处和Volatile03相比，volatile此种需要业务层面判断数值状态停止线程的，会出现此段判断代码不再执行导致无法停止。
 */
public class Volatile04 {

    public static void main(String[] args) throws InterruptedException {
        ArrayBlockingQueue<Integer> queue = new ArrayBlockingQueue<>(8);
        //启动生产者
        Producer producer = new Producer(queue);
        Thread producerThread = new Thread(producer);
        producerThread.start();
        //消费者
        Consumer consumer = new Consumer(queue);
        while (consumer.needMoreNumbers()) {
            System.out.println(consumer.queue.take() + " 被Consumer消费！！！");
            Thread.sleep(100);
        }
        System.out.println("Consume Done!!!");

        producer.canceled = true;
        System.out.println(producer.canceled);

    }

    static class Producer implements Runnable {
        //volatile修饰的变量
        public volatile boolean canceled = false;
        //阻塞队列
        BlockingQueue<Integer> queue;

        public Producer(BlockingQueue<Integer> queue) {
            this.queue = queue;
        }

        @Override
        public void run() {
            int num = 0;
            try {
                while (num < 1000000 && !canceled) {
                    if (num % 50 == 0) {
                        queue.put(num);
                        System.out.println(num + " add to the queue！！！！");
                    }
                    num++;
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                System.out.println("Producer has been stopped!!!");
            }
        }
    }

    static class Consumer {
        //共用阻塞队列
        BlockingQueue<Integer> queue;

        public Consumer(BlockingQueue<Integer> queue) {
            this.queue = queue;
        }

        public boolean needMoreNumbers() {
            return !(Math.random() > 0.97);
        }
    }
}
