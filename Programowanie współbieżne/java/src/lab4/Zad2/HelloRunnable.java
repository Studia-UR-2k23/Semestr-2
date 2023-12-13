package lab4.Zad2;

public class HelloRunnable implements Runnable {
    private Boolean waitForHello;
    public HelloRunnable(Boolean waitForHello) {
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
