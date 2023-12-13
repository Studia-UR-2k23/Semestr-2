package lab6;

import java.util.LinkedList;
import java.util.Random;

public class ProducerConsumerWait {
    static LinkedList<String> buffer = new LinkedList<>();
    static final int BUFFER_SIZE = 10;
    public static void main(String[] args) {
        class Producer extends Thread {
            private String name;
            public Producer(String name) {
                this.name = name;
            }
            @Override
            public void run() {
                while (true) {
                    synchronized (buffer) {
                        if (buffer.size() == BUFFER_SIZE) {
                            try {
                                buffer.wait();
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
                            buffer.notify();
                        }
                        try {
                            Thread.sleep(10);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
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
                    synchronized (buffer) {
                        if (buffer.size() == 0) {
                            try {
                                buffer.wait();
                                System.out.println("Consumer " + name + " is waiting, empty buffer");
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        } else {
                            String elem = buffer.getFirst();
                            System.out.println(this.getPriority());
                            System.out.println("Consumer" + name + " consumes: " + elem + " and process it to: " + elem.toUpperCase());
                            try {
                                Thread.sleep(10);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                            buffer.removeFirst();
                            buffer.notify();
                        }
                    }
                }
            }
        }
        new Consumer("I").start();
        new Producer("I").start();
        new Producer("II").start();
        new Producer("III").start();
        new Producer("IV").start();
    }
}