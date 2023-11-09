package Zad2;

public class ThreadHelloWorld {
    public static void main(String [] args) {
        HelloThread helloThread = new HelloThread(Boolean.FALSE);
        WorldThread worldThread = new WorldThread(Boolean.FALSE);
        worldThread.start();
        helloThread.start();
    }
}
