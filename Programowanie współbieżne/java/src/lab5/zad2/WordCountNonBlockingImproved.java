package lab5.zad2;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.ReentrantLock;

public class WordCountNonBlockingImproved {

    public static void main(String[] args) throws Exception {
        String text = "This is some text. It is intended to be counted by a threads.\n" +
                "Each thread will be taking a line and then will count words.\n" +
                "It is supposed to be faster than a standard, sequential way.\n" +
                "This example is non blocking and should be different from\n" +
                "the previous one.\n";
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
            int begin, end;

            public Counter(int begin, int end) {
                this.begin = begin;
                this.end = end;
            }

            @Override
            public void run() {
                for (int i = begin; i < end; ) {
                    String line = lines.get(i);
                    System.out.println("I am thread" + this.getId() + " and i got line " + line);

                    HashMap<String, Integer> localWordsCounted = new HashMap<>();
                    String[] splittedWords = line.split(" ");

                    for (String s : splittedWords) {
                        if (localWordsCounted.containsKey(s)) {
                            localWordsCounted.put(s, localWordsCounted.get(s) + 1);
                        } else {
                            localWordsCounted.putIfAbsent(s, 1);
                        }
                    }

                    try {
                        lock.lock();
                        wordsCounted.putAll(localWordsCounted);
                        lock.unlock();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    i++;
                }

                try {
                    System.out.println(this.getId() + " called await");
                    cb.await();
                } catch (InterruptedException | BrokenBarrierException e) {
                    e.printStackTrace();
                }
            }
        }

        ExecutorService executor = Executors.newFixedThreadPool(numberOfThreads);
        executor.submit(new Counter(0, 2));
        executor.submit(new Counter(2, 3));
        executor.submit(new Counter(3, lines.size()));
        executor.shutdown();

        if (executor.isShutdown()) {
            for (String s : wordsCounted.keySet()) {
                System.out.println(s + " has " + wordsCounted.get(s) + " occurrences.");
            }
        }
    }
}