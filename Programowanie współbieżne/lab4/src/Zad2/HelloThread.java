package Zad2;

public class HelloThread extends Thread {
    private Boolean waitForHello;
    public HelloThread(Boolean waitForHello) {
        this.waitForHello = waitForHello;
    }
    @Override
    public void run() {
        synchronized (waitForHello) {
            System.out.print("Hello");
            waitForHello.notify();
        }
    }
}
