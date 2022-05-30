package com.example.mistakes.demos;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;
import java.util.stream.Collectors;
import java.util.stream.IntStream;


@Slf4j
@RestController
@RequestMapping("deadlock")
public class DeadLockDemo {

    /**
     * 新建一个线程安全的容器类
     */
    private final ConcurrentHashMap<String, Item> items = new ConcurrentHashMap<>();

    public DeadLockDemo() {
        IntStream.range(0, 10).forEach(i -> items.put("item" + i, new Item("item" + i)));
    }

    private boolean createOrder(List<Item> order) {
        // 可重入锁的集合
        List<ReentrantLock> locks = new ArrayList<>();

        for (Item item : order) {
            try {
                if (item.lock.tryLock(10, TimeUnit.SECONDS)) {
                    // 尝试加锁，成功后将锁加入 locks
                    locks.add(item.lock);
                } else {
                    // 如加锁失败，释放锁有锁，并返回false
                    locks.forEach(ReentrantLock::unlock);
                    return false;
                }
            } catch (InterruptedException ignored) {
            }
        }

        try {
            order.forEach(item -> item.remaining--);
        } finally {
            locks.forEach(ReentrantLock::unlock);
        }
        return true;
    }

    private List<Item> createCart() {
        return IntStream.rangeClosed(1, 3)
                .mapToObj(i -> "item" + ThreadLocalRandom.current().nextInt(items.size()))
                .map(items::get).collect(Collectors.toList());
    }

    @GetMapping("wrong")
    public long wrong() {
        long begin = System.currentTimeMillis();
        long success = IntStream.rangeClosed(1, 100).parallel()
                .mapToObj(i -> {
                    List<Item> cart = createCart();
                    return createOrder(cart);
                })
                .filter(result -> result)
                .count();
        log.info("success:{} totalRemaining:{} took:{}ms items:{}",
                success,
                items.values().stream().map(item -> item.remaining).reduce(0, Integer::sum),
                System.currentTimeMillis() - begin, items);
        return success;
    }

    @GetMapping("right")
    public long right() {
        long begin = System.currentTimeMillis();
        long success = IntStream.rangeClosed(1, 100).parallel()
                .mapToObj(i -> {
                    List<Item> cart = createCart().stream()
                            .sorted(Comparator.comparing(Item::getName))
                            .collect(Collectors.toList());
                    return createOrder(cart);
                })
                .filter(result -> result)
                .count();
        log.info("success:{} totalRemaining:{} took:{}ms items:{}",
                success,
                items.values().stream().map(item -> item.remaining).reduce(0, Integer::sum),
                System.currentTimeMillis() - begin, items);
        return success;
    }

    @Data
    @RequiredArgsConstructor
    static class Item {
        final String name;
        int remaining = 1000;
        @ToString.Exclude
        ReentrantLock lock = new ReentrantLock();
    }
}