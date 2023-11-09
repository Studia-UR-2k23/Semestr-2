package Zad1;

import java.util.ArrayList;
public class ThreadsArrayHelloWorld {
    public static void main(String [] args) {
        final ArrayList<String> buffer = new ArrayList<>();
        HelloThreadArray helloThread = new HelloThreadArray(buffer);
        WorldThreadArray worldThread = new WorldThreadArray(buffer);
        worldThread.start();
        helloThread.start();
    }
}