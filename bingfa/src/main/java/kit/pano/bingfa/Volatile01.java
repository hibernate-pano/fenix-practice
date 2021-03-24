package kit.pano.bingfa;

/**
 * 测试volatile关键字是否是线程安全的，使用非原子操作类
 */
public class Volatile01 {

    public static volatile int num = 0;

    public static void main(String[] args) throws InterruptedException {
        for (int i = 0; i < 100; i++) {
            new Thread(
                    new Runnable() {
                        @Override
                        public void run() {
                            for (int j = 0; j < 20000; j++) {
                                num++;//此处线程不安全，非原子操作
                            }
                        }
                    }).start();
        }

        Thread.sleep(3000);
        System.out.println(num);
    }


    //期望值：2000000
    //实际值：315216
    //相差十倍
}