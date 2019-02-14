package com.jorado.logger.concurrent;

import java.util.concurrent.CountDownLatch;

/**
 * Created by Administrator on 2015/4/16.
 */
public class WatchedThread extends Thread {

    private CountDownLatch threadsSignal;
    private Runnable target;

    public WatchedThread(CountDownLatch threadsSignal, Runnable target) {
        this.threadsSignal = threadsSignal;
        this.target = target;
    }

    @Override
    public void run() {
        try {
            target.run();
        } catch (Exception ex) {

        }
        threadsSignal.countDown();
    }
}


