package kit.pano.practice;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * 测试volatile线程是否安全，使用原子操作类
 */
public class VolatileTest02 {

    //J.U.C包中线程安全类
    public static AtomicInteger num = new AtomicInteger(0);

    public static void main(String[] args) throws InterruptedException {
        for (int i = 0; i < 100; i++) {
            new Thread(
                    new Runnable() {
                        @Override
                        public void run() {
                            for (int j = 0; j < 20000; j++) {
                                num.incrementAndGet();
                            }
                        }
                    }
            ).start();
        }

        Thread.sleep(3000);
        System.out.println(num);
    }

    //期望值：2000000
    //实际值：2000000
    //完美
}
