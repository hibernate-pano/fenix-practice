package kit.pano.bingfa;

/**
 * 正确使用interrupt停止线程
 */
public class Interrupt01 implements Runnable {

    @Override
    public void run() {
        int counter = 0;
        while (!Thread.currentThread().isInterrupted() && counter < 1000) {
            counter++;
            System.out.println(counter);
        }
    }

    public static void main(String[] args) throws InterruptedException {
        Thread thread = new Thread(new Interrupt01());
        thread.start();
        Thread.sleep(5);
        thread.interrupt();
    }
}
