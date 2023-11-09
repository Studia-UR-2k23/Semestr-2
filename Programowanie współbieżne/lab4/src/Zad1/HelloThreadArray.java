package Zad1;

import java.util.ArrayList;
public class HelloThreadArray extends Thread {
    private ArrayList<String> buffer;
    public HelloThreadArray(ArrayList<String> buffer) {
        this.buffer = buffer;
    }
    @Override
    public void run() {
        synchronized (buffer) {
            buffer.add("Hello, ");
            buffer.notify();
        }
    }
}