package Zad2;

public class WorldThread extends Thread {
    private Boolean waitForHello;
    public WorldThread(Boolean waitForHello) {
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
