package kit.pano.bingfa;

import java.util.LinkedList;
import java.util.Queue;

/**
 * 通过wait和notifyAll完成自定义BlockingQueue
 */
public class MyBlockingQueue02 {

    private int max;
    private Queue<Integer> queue;

    public MyBlockingQueue02(int max) {
        this.max = max;
        queue = new LinkedList<>();
    }

    public synchronized void put(int i) throws InterruptedException {
        while (queue.size() == max) {
            this.wait();
        }
        queue.add(i);
        this.notifyAll();
    }


    public synchronized Integer take() throws InterruptedException {
        while (queue.size() == 0) {
            this.wait();
        }

        Integer remove = queue.remove();
        this.notifyAll();
        return remove;
    }
}
