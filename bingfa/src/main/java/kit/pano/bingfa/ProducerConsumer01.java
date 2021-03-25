package kit.pano.bingfa;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * 实现生产者消费者模式
 * 最简单的版本
 */
public class ProducerConsumer01 {

    public static void main(String[] args) {

//        BlockingQueue<Integer> queue = new ArrayBlockingQueue<>(16);

        //使用通过Condition自定义的MyBlockingQueue01
//        MyBlockingQueue01 queue = new MyBlockingQueue01(16);

        //使用通过wait和notifyAll自定义的MyBlockingQueue02
        MyBlockingQueue02 queue = new MyBlockingQueue02(16);

        AtomicInteger number = new AtomicInteger(0);

        Runnable producer = () -> {
            while (true) {
                //增加同步锁，避免线程安全问题
                synchronized (ProducerConsumer01.class) {
                    try {
                        queue.put(number.incrementAndGet());
                        System.out.println("add " + number);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        };

        //启动线程
        new Thread(producer).start();
        new Thread(producer).start();
        new Thread(producer).start();

        Runnable consumer = () -> {
            while (true) {
                try {
                    System.out.println("remove " + queue.take());
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };

        //启动线程
        new Thread(consumer).start();
        new Thread(consumer).start();
        new Thread(consumer).start();
        new Thread(consumer).start();
        new Thread(consumer).start();

    }

}
