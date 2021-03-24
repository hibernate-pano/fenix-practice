package kit.pano.bingfa;

/**
 * 通过volatile标记为实现线程中断
 *
 * 不推荐。因为适用场景有限制。详见Volatile04
 */
public class Volatile03 implements Runnable {

    private volatile boolean canceled = false;

    @Override
    public void run() {
        int number = 0;
        while (!canceled && number < 1000000) {
            if (number % 10 == 0) {
                System.out.println(number);
            }
            number++;
        }
    }

    public static void main(String[] args) throws InterruptedException {
        Volatile03 demo01 = new Volatile03();
        Thread t = new Thread(demo01);
        t.start();
        Thread.sleep(5);
        demo01.canceled = true;
    }
}
