package Zad2;

public class WorldRunnable implements Runnable {
    private Boolean waitForHello;
    public WorldRunnable(Boolean waitForHello) {
        this.waitForHello = waitForHello;
    }
    @Override
    public void run() {
        synchronized (this.waitForHello) {
            try {
                this.waitForHello.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(", World!");
        }
    }
}
