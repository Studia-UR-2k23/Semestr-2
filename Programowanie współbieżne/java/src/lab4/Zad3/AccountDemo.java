package lab4.Zad3;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class AccountDemo {
    public static void main(String [] args) throws InterruptedException {
        Account account = new Account(500, 3000);
        ExecutorService executor = Executors.newFixedThreadPool(20);
        executor.execute(new Thread(() -> {account.subtract(1000);}));
        executor.execute(new Thread(() -> {account.subtract(500);}));
        executor.execute(new Thread(() -> {account.subtract(200);}));
        executor.execute(new Thread(() -> {account.subtract(1200);}));
        executor.execute(new Thread(() -> {account.subtract(1500);}));
        executor.shutdown();
    }
}
