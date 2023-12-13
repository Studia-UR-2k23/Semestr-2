package lab6;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Random;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class ProducerConsumerNonBlocking {
    static LinkedList<String> buffer = new LinkedList<>();
    static final int BUFFER_SIZE = 10;
    static ReentrantLock lock = new ReentrantLock(true);
    static Condition bufferNotFull = lock.newCondition();
    static Condition bufferNotEmpty = lock.newCondition();
    public static void main(String[] args) {
        class Producer extends Thread {
            private String name;
            private ArrayList<String> localItems;

            public Producer(String name) {
                this.name = name;
                this.localItems = new ArrayList<>();
            }

            void produceElem() {
                Random r = new Random();
                String[] words = {"aaa", "bbb", "ccc", "ddd", "eee", "fff"};
                localItems.add(words[r.nextInt(words.length)]);
            }

            void produce() {
                if (localItems.size() < 10) {
                    produceElem();
                }
            }

            @Override
            public void run() {
                while (true) {
                    if (localItems.size() == 0) {
                        System.out.println("Producer has 0 items");
                    }
                    boolean gained = lock.tryLock();
                    if (gained) {
                        if (buffer.size() == BUFFER_SIZE) {
                            try {
                                bufferNotFull.await();
                                System.out.println("Producer " + name + " is waiting, full buffer");
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        } else {
                            System.out.println("Producer added items");
                            buffer.addAll(localItems);
                            localItems.clear();
                            bufferNotEmpty.signal();
                        }
                        lock.unlock();
                    }
                    produce();
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
                    boolean gained = lock.tryLock();
                    if (gained) {
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
        }
        new Producer("I").start();
        new Producer("II").start();
        new Producer("III").start();
        new Consumer("I").start();
        new Consumer("II").start();
        new Consumer("III").start();
        new Consumer("IV").start();
    }
}
