package kit.pano.bingfa;

/**
 * 并发售票问题
 */
public class ThreadDemo01 implements Runnable {

    private int tickets = 100;

    @Override
    public void run() {
        while (true) {
            sellTickets();
        }
    }

    private synchronized void sellTickets() {
        if (tickets > 0) {
            System.out.println(Thread.currentThread().getName() + tickets);
            tickets--;
        }
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
