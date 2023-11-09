package lab4.zad4;
import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.*;
public class CallableTemperatureSumming {
    public static void main(String [] args) throws ExecutionException, InterruptedException,
            TimeoutException {
        ExecutorService executor = Executors.newFixedThreadPool(20);
        ArrayList<Integer> temperature = new ArrayList<>();
        Random r = new Random();
        for (int i = 0; i < 100000; i++) { temperature.add(r.nextInt(35)); }
        Future<Integer> part1 = executor.submit(() -> {
            int partSum = 0;
            for (int i = 0; i < 25000; i++) { partSum += temperature.get(i); }
            return partSum;
        });
        Future<Integer> part2 = executor.submit(() -> {
            int partSum = 0;
            for (int i = 25000; i < 50000; i++) { partSum += temperature.get(i); }
            return partSum;
        });
        Future<Integer> part3 = executor.submit(() -> {
            int partSum = 0;
            for (int i = 50000; i < 75000; i++) { partSum += temperature.get(i); }
            return partSum;
        });
        Future<Integer> part4 = executor.submit(() -> {
            int partSum = 0;
            for (int i = 75000; i < 100000; i++) { partSum += temperature.get(i); }
            return partSum;
        });
        while (!part1.isDone() && !part2.isDone() && !part3.isDone() && !part4.isDone()) {
            Thread.sleep(0, 1);
        }
        int s = part1.get(1, TimeUnit.NANOSECONDS);
        int s2 = part2.get(1, TimeUnit.NANOSECONDS);
        int s3 = part3.get(1, TimeUnit.NANOSECONDS);
        int s4 = part4.get(1, TimeUnit.NANOSECONDS);
        System.out.println("The mean temperature is = " + (s + s2 + s3 + s4) / 100000.0);
        executor.shutdown();
    }
}
