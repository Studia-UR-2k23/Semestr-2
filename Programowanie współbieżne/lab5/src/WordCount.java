import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.concurrent.*;
import java.util.concurrent.locks.ReentrantLock;

public class WordCount {
    public static void main(String[] args) {
        String text = "This is some text. It is intended to be counted by a threads.\n" +
                "Each thread will be taking a line and then will count words.\n" +
                "It is supposed to be faster than a standard, sequential way";
        ArrayList<String> lines = new ArrayList<>(Arrays.asList(text.split("\n")));
        HashMap<String, Integer> wordsCounted = new HashMap<>();
        ReentrantLock lock = new ReentrantLock(true);
        int numberOfThreads = 3;
        CyclicBarrier cb = new CyclicBarrier(numberOfThreads, () -> {
            for (String s : wordsCounted.keySet()) {
                if (wordsCounted.get(s) > 1) {
                    System.out.println(s + " has " + wordsCounted.get(s) + " occurrences.");
                } else {
                    System.out.println(s + " has " + wordsCounted.get(s) + " occurrence.");
                }
            }
        });
        class Counter extends Thread {
            @Override
            public void run() {
                while (!lines.isEmpty()) {
                    lock.lock();
                    try {
                        if (!lines.isEmpty()) {
                            System.out.println("I am thread" + this.getId());
                            System.out.println("I got line: " + lines.get(0));
                            for (String s : lines.get(0).split(" ")) {
                                if (wordsCounted.containsKey(s)) {
                                    wordsCounted.put(s, wordsCounted.get(s) + 1);
                                } else {
                                    wordsCounted.putIfAbsent(s, 1);
                                }
                            }
                            lines.remove(0);
                        }
                    } finally {
                        lock.unlock();
                        try {
                            cb.await();
                        } catch (InterruptedException | BrokenBarrierException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }
        ExecutorService executor = Executors.newFixedThreadPool(numberOfThreads);
        executor.submit(new Counter());
        executor.submit(new Counter());
        executor.submit(new Counter());
        executor.shutdown();
    }
}
