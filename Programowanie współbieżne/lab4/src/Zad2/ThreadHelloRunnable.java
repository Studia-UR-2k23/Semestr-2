package Zad2;

public class ThreadHelloRunnable {
    public static void main(String [] args) {
        Boolean waitForHello = Boolean.FALSE;
        HelloRunnable helloRunnable = new HelloRunnable(waitForHello);
        WorldRunnable worldRunnable = new WorldRunnable(waitForHello);
        Thread worldThread = new Thread(worldRunnable);
        Thread helloThread = new Thread(helloRunnable);
        worldThread.start();
        helloThread.start();
    }
}
