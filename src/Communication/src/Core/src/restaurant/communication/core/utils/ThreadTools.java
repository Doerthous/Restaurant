package restaurant.communication.core.utils;

import static java.lang.Thread.sleep;

public class ThreadTools {
    private static void loopThreadWrapper(int count, int interval, Runnable runnable){
        for(int i = 0; i < count; ++i){
            runnable.run();
            try {
                sleep(interval);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
    public static void loopThread(int count, int interval, Runnable runnable){
        new Thread(()->{
            loopThreadWrapper(count, interval, runnable);
        }).start();
    }
    public static void loopThread(int count, int interval, Runnable runnable, Runnable after){
        new Thread(()->{
            loopThreadWrapper(count, interval, runnable);
            after.run();
        }).start();
    }
}
