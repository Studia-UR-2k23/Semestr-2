import java.util.LinkedList;
import java.util.Random;

public class ProducerConsumerExactlyRuns {
    static LinkedList<String> buffer = new LinkedList<>();
    static final int BUFFER_SIZE = 10;
    public static void main(String[] args) {
        class Producer extends Thread {
            private String name;
            private int job_counter;
            public Producer(String name, int job_counter) {
                this.name = name;
                this.job_counter = job_counter;
            }
            @Override
            public void run() {
                int i = 0;
                while (i < job_counter) {
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
                            i++;
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
            private int job_counter;
            public Consumer(String name, int job_counter) {
                this.name = name;
                this.job_counter = job_counter;
            }

            @Override
            public void run() {
                int i = 0;
                while (i < job_counter) {
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
                            System.out.println("Consumer" + name + " consumes: " + elem + " and process it to: " + elem.toUpperCase());
                            try {
                                Thread.sleep(10);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                            i++;
                            buffer.removeFirst();
                            buffer.notify();
                        }
                    }
                }
            }
        }
        new Consumer("I", 2).start();
        new Producer("II", 1).start();
        new Producer("I", 3).start();
        new Consumer("II", 2).start();

    }
}