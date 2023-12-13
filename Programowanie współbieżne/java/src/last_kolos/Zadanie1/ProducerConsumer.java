package last_kolos.Zadanie1;

import java.util.LinkedList;
import java.util.Random;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class ProducerConsumer {
    static LinkedList<String> buffer = new LinkedList<>();
    static final int BUFFER_SIZE = 5;
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
                        String[] words = {"k", "a", "r", "o", "l", "b", "u", "r", "y"};
                        String data = "10.01.2023";
                        String album = "106421";
                        String s = words[r.nextInt(words.length)]+words[r.nextInt(words.length)]+words[r.nextInt(words.length)]+
                                words[r.nextInt(words.length)]+words[r.nextInt(words.length)]+words[r.nextInt(words.length)]+
                                words[r.nextInt(words.length)]+words[r.nextInt(words.length)];
                        System.out.println("Producent " + name + " wygenerował : " + data + " " + album + " " + s);
                        String str = data+" " + album + " " + s;
                        buffer.addLast(str);
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
                        System.out.println(this.getPriority());
                        int countB = 0;
                        int countG = 0;
                        int countK = 0;
                        int countZ = 0;
                        int count3 = 0;
                        int count4 = 0;
                        int count7 = 0;
                        int count8 = 0;

                        for (var i : elem.toCharArray()){
                            switch(i) {
                                case 'b':
                                    countB++;
                                    break;
                                case 'g':
                                    countG++;
                                    break;
                                case 'k':
                                    countK++;
                                    break;
                                case 'z':
                                    countZ++;
                                    break;
                                case '3':
                                    count3++;
                                    break;
                                case '4':
                                    count4++;
                                    break;
                                case '7':
                                    count7++;
                                    break;
                                case '8':
                                    count8++;
                                    break;
                                default:
                            }
                        }

                        System.out.println("Konsument " + name + " skonsumował : " +elem +  " i wyznaczył: "
                                + " liczba b : " + countB + ", liczba g : "+ countG + ", liczba k : " + countK + ", liczba z : " + countZ  +
                                ", liczba 3 : " + count3 + ", liczba 4 : "+ count4 + ", liczba 7 : " + count7 + ", liczba 8 : " + count8 );
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
        new Consumer("II").start();
    }
}