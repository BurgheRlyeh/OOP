package ru.nsu.fit.oop.maximov.pizzeria;

import ru.nsu.fit.oop.maximov.pizzeria.order.Order;
import ru.nsu.fit.oop.maximov.pizzeria.synchronizedqueue.SynchronizedQueue;
import ru.nsu.fit.oop.maximov.pizzeria.worker.Worker;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * The main class of the pizzeria, all interaction with the enterprise occurs through it
 */
public class Pizzeria {
    private final SynchronizedQueue<Order> orders;
    private final SynchronizedQueue<Order> storage;

    ThreadPoolExecutor bakers;
    ThreadPoolExecutor couriers;

    /**
     * @param config config of pizzeria
     */
    public Pizzeria(PizzeriaConfig config) {
        orders = new SynchronizedQueue<>();
        storage = new SynchronizedQueue<>(config.storageSize);

        bakers = (ThreadPoolExecutor) Executors.newFixedThreadPool(config.bakers.length);
        Arrays.stream(config.bakers)
                .forEach(workerConfig -> bakers.submit(new Worker(workerConfig, orders, storage, "Baker")));

        couriers = (ThreadPoolExecutor) Executors.newFixedThreadPool(config.couriers.length);
        Arrays.stream(config.couriers)
                .forEach(workerConfig -> couriers.submit(new Worker(workerConfig, storage, null, "Courier")));
    }

    /**
     * @param orders collection of orders
     * @param message communication about the production/delivery process
     * @param sender a class that reports the status of an order
     */
    public static void log(Collection<Order> orders, String message, String sender) {
        if (orders.size() == 1) {
            System.out.println("Order " + orders + " is " + message + " by " + sender);
            return;
        }
        System.out.println("Orders " + orders + " are " + message + " by " + sender);
    }

    /**
     * @param order order to put
     */
    public void putOrder(Order order) {
        log(List.of(order), "put", "client");
        orders.enqueue(order);
    }

    /**
     * a function that stops the entire production process in the enterprise
     */
    public void stop() {
        bakers.shutdownNow();
        couriers.shutdownNow();
    }
}