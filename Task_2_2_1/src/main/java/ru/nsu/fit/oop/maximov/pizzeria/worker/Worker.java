package ru.nsu.fit.oop.maximov.pizzeria.worker;

import ru.nsu.fit.oop.maximov.pizzeria.order.Order;
import ru.nsu.fit.oop.maximov.pizzeria.synchronizedqueue.SynchronizedQueue;

import static ru.nsu.fit.oop.maximov.pizzeria.Pizzeria.log;

/**
 * a worker class that takes work from the source, executes it, and (optionally) puts it in the work destination
 */
public class Worker implements Runnable {
    public int workerID;
    public int workTime;
    public int parallelWorkAmount;
    public String workerType;
    SynchronizedQueue<Order> workSrc;
    SynchronizedQueue<Order> workDest;

    /**
     * @param config config of worker
     * @param workSrc source of work
     * @param workDest destination of work
     */
    public Worker(WorkerConfig config, SynchronizedQueue<Order> workSrc, SynchronizedQueue<Order> workDest) {
        this(config, workSrc, workDest, "Worker");
    }

    /**
     * @param config config of worker
     * @param workSrc source of work
     * @param workDest destination of work
     * @param workerType type of worker
     */
    public Worker(WorkerConfig config, SynchronizedQueue<Order> workSrc, SynchronizedQueue<Order> workDest, String workerType) {
        workerID = config.ID;
        workTime = config.workTime;
        parallelWorkAmount = Math.max(config.parallelWorkAmount, 1);
        this.workSrc = workSrc;
        this.workDest = workDest;
        this.workerType = workerType;
    }

    @Override
    public void run() {
        while (!Thread.currentThread().isInterrupted()) {
            try {
                var orders = workSrc.tryDequeue(parallelWorkAmount);
                log(orders, "accepted", this.toString());

                for (int i = 0; i < parallelWorkAmount; ++i) {
                    Thread.sleep(workTime);
                }
                log(orders, "completed", this.toString());

                if (workDest != null) {
                    log(orders, "passed", this.toString());
                    workDest.enqueue(orders);
                }
            } catch (InterruptedException ignored) {
            }
        }
    }

    @Override
    public String toString() {
        return workerType + " " + workerID;
    }
}