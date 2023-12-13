package lab6;

import java.util.LinkedList;
import java.util.Random;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class ProducerConsumerLock {
    static LinkedList<String> buffer = new LinkedList<>();
    static final int BUFFER_SIZE = 20;
    static ReentrantLock lock = new ReentrantLock(true);
    static Condition bufferNotFull = lock.newCondition();
    static Condition bufferNotEmpty = lock.newCondition();
    public static void main(String[] args) {
        class Producer extends Thread {
            private String name;
            public Producer(String name) {
                this.name = name;
            }
            @Override
            public void run() {
                while (true) {
                    lock.lock();
                    if (buffer.size() == BUFFER_SIZE) {
                        try {
                            bufferNotFull.await();
                            System.out.println("Producer " + name + " is waiting, full buffer");
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    } else {
                        Random r = new Random();
                        String[] words = {"aaa", "bbb", "ccc", "ddd", "eee", "fff"};
                        String s = words[r.nextInt(words.length)];
                        System.out.println("Producer " + name + " produces: " + s);
                        buffer.addLast(s);
                        bufferNotEmpty.signal();
                    }
                    lock.unlock();
                    try {
                        Thread.sleep(10);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        class Consumer extends Thread {
            private String name;
            public Consumer(String name) {
                this.name = name;
            }
            @Override
            public void run() {
                while (true) {
                    lock.lock();
                    if (buffer.size() == 0) {
                        System.out.println("Consumer " + name + " is waiting, empty buffer");
                        try {
                            bufferNotEmpty.await();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    } else {
                        String elem = buffer.getFirst();
                        System.out.println("Consumer" + name + " consumes: " + elem + " and process it to: "
                                + elem.toUpperCase());
                        try {
                            Thread.sleep(10);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        buffer.removeFirst();
                        bufferNotFull.signal();
                    }
                    lock.unlock();
                }
            }
        }
        new Producer("I").start();
        new Producer("II").start();
        new Producer("III").start();
        new Consumer("I").start();
    }
}