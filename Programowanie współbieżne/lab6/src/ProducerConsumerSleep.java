import java.util.LinkedList;
import java.util.Random;
public class ProducerConsumerSleep {
    static LinkedList<String> buffer = new LinkedList<>();
    static final int BUFFER_SIZE = 10;
    public static void main(String[] args) {
        class Producer extends Thread {
            private String name;
            public Producer(String name) {
                this.name = name;
//                this.setPriority(Thread.MAX_PRIORITY);
            }
            @Override
            public void run() {
                while (true) {
                    synchronized (buffer) {
                        if (buffer.size() == BUFFER_SIZE) {
                            try {
                                Thread.sleep(10);
                                System.out.println("Producer " + name + " is sleeping, full buffer");
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        } else {
                            Random r = new Random();
                            String[] words = {"aaa", "bbb", "ccc", "ddd", "eee", "fff"};
                            String s = words[r.nextInt(words.length)];
                            buffer.addLast(s);
                            System.out.println("Producer " + name + " produces: " + s);
                        }
                    }
                }
            }
        }
        class Consumer extends Thread {
            private String name;
            public Consumer(String name) {
                this.name = name;
//                this.setPriority(Thread.MIN_PRIORITY);
            }
            @Override
            public void run() {
                while (true) {
                    synchronized (buffer) {
                        if (buffer.size() == 0) {
                            try {
                                Thread.sleep(10);
                                System.out.println("Consumer " + name + " is sleeping, empty buffer");
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        } else {
                            String elem = buffer.getFirst();
                            buffer.removeFirst();
                            System.out.println("Consumer" + name + " consumes: " + elem + " and process it to: " + elem.toUpperCase());
                        }
                    }
                }
            }
        }
        new Consumer("I").start();
        new Producer("I").start();
        new Consumer("II").start();
        new Consumer("III").start();
        new Consumer("IV").start();
        new Consumer("V").start();
    }
}