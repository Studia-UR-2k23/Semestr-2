package lab4.Zad1;

import java.util.ArrayList;
public class WorldThreadArray extends Thread {
    private ArrayList<String> buffer;
    public WorldThreadArray(ArrayList<String> buffer) {
        this.buffer = buffer;
    }
    @Override
    public void run() {
        synchronized (buffer) {
            try {
                buffer.wait();
                buffer.add("World!");
                for (String s : buffer)
                    System.out.print(s);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}