package lab4.zad6;

public class HotSpotTestVolatile {
    static volatile long count;
    static  volatile boolean shouldContinue = true;
    public static void main(String[] args) {
        Thread t = new Thread(new Runnable() {
            public void run() {
                while(shouldContinue) {
                    count++;
                }
                System.out.println("I exited");
            }
        });
        t.start();
        do {
            try {
                Thread.sleep(1000L);
            } catch(InterruptedException ie) {}
        } while(count < 999999L);
        shouldContinue = false;
        System.out.println(
                "stopping at " + count + " iterations"
        );
        try {
            t.join();
        } catch(InterruptedException ie) {}
    }

}
