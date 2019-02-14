package com.jorado.core.multitask;


import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.*;

public abstract class AbsParallelCalculater<T, Y> {
    static ExecutorService se = Executors.newCachedThreadPool();
    protected int itemcount = 10;
    protected int maxcount = 5;
    protected int timeout = 3;


    CountDownLatch counter;
    List<T> waitList;
    Y resume;

    Queue<T> queue = new PriorityBlockingQueue<T>(100, new Comparator<T>() {

        @Override
        public int compare(T o1, T o2) {
            return compareItem(o1, o2);
        }

    });

    public Queue<T> getSequenceQueue() {
        return queue;
    }

    public void start(List<T> list, Y resume) {
        this.waitList = list;
        this.resume = resume;
        try {
            List<SubCalculater> calculaters = getSubCalculaters();
            if (calculaters.size() > 0) {
                counter = new CountDownLatch(calculaters.size());
                for (SubCalculater subc : calculaters) {
                    se.execute(subc);
                }
                counter.await(timeout, TimeUnit.SECONDS);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public abstract float calculate(T position, Y resume);

    public abstract int compareItem(T o1, T o2);

    List<SubCalculater> getSubCalculaters() {
        List<SubCalculater> calculaters = new ArrayList<>();
        int start = 0;
        while (start < waitList.size()) {
            int end = start + itemcount < waitList.size() ? start
                    + itemcount : waitList.size();
            SubCalculater subc = this.new SubCalculater(start, end);
            calculaters.add(subc);
            start = end;
        }
        return calculaters;
    }

    class SubCalculater implements Runnable {
        public SubCalculater(int start, int end) {
            this.start = start;
            this.end = end;
        }

        int start;
        int end;

        @Override
        public void run() {
            //System.out.println("线程id" + Thread.currentThread().getId());
            try {
                for (int i = start; i < end; i++) {
                    if (queue.size() >= maxcount)
                        break;
                    T position = waitList.get(i);
                    calculate(position, resume);
                    queue.add(position);
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                counter.countDown();
            }
        }
    }
}
