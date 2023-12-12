import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Random;
import java.util.Scanner;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class ReaderWriterLock {
    public static void main(String[] args) throws InterruptedException {
        String lineSep = System.lineSeparator();
        File f = new File("temp.txt");
        ReentrantLock lock = new ReentrantLock(true);
        Condition fileExist = lock.newCondition();
        class Reader extends Thread {
            String name;
            public Reader(String name) {
                this.name = name;
            }
            @Override
            public void run() {
                while (lock.isLocked());
                lock.lock();
                try {
                    if (!f.exists()) {
                        fileExist.await();
                    }
                    FileReader fileReader = new FileReader(f);
                    String fileContent;
                    Scanner scanner = new Scanner(fileReader);
                    StringBuilder sb = new StringBuilder();
                    while (scanner.hasNextLine()) {
                        sb.append(scanner.nextLine());
                        sb.append(lineSep);
                    }
                    fileContent = sb.toString();
                    fileReader.close();
                    System.out.println("Reader " + name + " reads: ");
                    System.out.print("\u001B[32m");
                    System.out.print(fileContent);
                    System.out.print("\u001B[0m");

                } catch (InterruptedException | IOException e) {
                    e.printStackTrace();
                } finally {
                    lock.unlock();
                }
            }
        }
        class Writer extends Thread {
            String name;
            public Writer(String name) {
                this.name = name;
            }
            @Override
            public void run() {
                boolean fileExst = f.exists();
                lock.lock();
                try {
                    Random r = new Random();
                    String[] words = {"aaa", "bbb", "ccc", "ddd", "eee", "fff"};
                    String s = words[r.nextInt(words.length)];
                    String fileContent = "";
                    if (fileExst) {
                        FileReader fileReader = new FileReader(f);
                        Scanner scanner = new Scanner(fileReader);
                        StringBuilder sb = new StringBuilder();
                        while (scanner.hasNextLine()) {
                            sb.append(scanner.nextLine());
                            sb.append(lineSep);
                        }
                        fileContent = sb.toString();
                        fileReader.close();
                    }
                    FileWriter fileWriter = new FileWriter(f);
                    fileWriter.write(fileContent + "Writer " + name + " writes: " + s + lineSep);
                    System.out.print("\u001B[34m");
                    System.out.println("Writer " + name + " reads: ");
                    System.out.print(fileContent);
                    System.out.print("\u001B[0m");
                    System.out.print("\u001b[31m");
                    System.out.println("Writer " + name + " appends: " + s);
                    System.out.print("\u001B[0m");
                    fileWriter.close();
                    if (!fileExst) {
                        System.out.println("Number of readers waiting for create file: " +
                                lock.getWaitQueueLength(fileExist));
                        fileExist.signalAll();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    lock.unlock();
                }
            }
        }
        Reader reader = new Reader("I");
        reader.start();
        Reader reader2 = new Reader("II");
        reader2.start();
        Reader reader3 = new Reader("III");
        reader3.start();
        Writer writer = new Writer("I");
        writer.start();
        Reader reader4 = new Reader("IV");
        reader4.start();
        Reader reader5 = new Reader("V");
        reader5.start();
        Reader reader6 = new Reader("VI");
        reader6.start();

        Writer writer2 = new Writer("II");
        writer2.start();
        Thread.sleep(1000);
        try {
            Files.deleteIfExists(f.toPath().toAbsolutePath());
            System.out.println("File deleted: "+ f.getPath());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
}