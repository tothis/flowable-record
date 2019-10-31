package com.example.util;


import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * @author lilei
 */
public class ThreadUtil {

    public static final ScheduledExecutorService threadPool = Executors.newScheduledThreadPool(Runtime.getRuntime().availableProcessors() * 2);

    /**
     * @param callback
     * @author lilei
     * @Description: 线程立即执行
     */
    public static void executeInThread(Callback callback) {
        threadPool.execute(new Runnable() {
            @Override
            public void run() {
                callback.execute(Thread.currentThread().getName());
            }
        });
    }

    /**
     * @param callback
     * @param obj
     * @return void
     * @author lilei
     * @time 2019/10/30 13:15
     * @description
     */
    public static void executeInThread(Callback callback, Object obj) {
        threadPool.execute(new Runnable() {
            @Override
            public void run() {
                callback.execute(obj);
            }
        });
    }

    /**
     * @param callback
     * @param delay    延时(单位:秒)
     * @author lilei
     * @time 2019/10/30 13:08
     * @description 延时执行线程
     */
    public static void executeInThread(Callback callback, long delay) {
        threadPool.schedule(new Runnable() {
            @Override
            public void run() {
                callback.execute(Thread.currentThread().getName());
            }
        }, delay, TimeUnit.SECONDS);
    }

    interface Callback {
        void execute(Object obj);
    }

    public static void main(String[] args) {
        int[] numbers = new int[10];
        for (int i = 0; i < 10; i++) {
            numbers[i] = i + 1;
        }

        for (int number : numbers) {
            ThreadUtil.executeInThread(
                    new Callback() {
                        @Override
                        public void execute(Object obj) {
                            System.out.println(number);
                        }
                    }
            );
        }

        // Arrays.stream(numbers).forEach(System.out::println);
    }
}