package lab5.zad3;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class InvoiceGeneratorAndSender {
    public static void main(String[] args) {
        ArrayList<Invoice> invoices = new ArrayList<>();
        LinkedList<Invoice> invoicesWithPdf = new LinkedList<>();
        final ReentrantLock lock = new ReentrantLock(true);
        final Condition createdInvoicePdf = lock.newCondition();
        AtomicInteger noInvoicesCreated = new AtomicInteger();
        AtomicInteger noPdfCreated = new AtomicInteger();
        AtomicInteger noEmailsSend = new AtomicInteger();

        //      ***  Generator faktur   ***
        new Thread(() -> {
            while (noInvoicesCreated.get() < 10) {
                boolean aquired = false;
                try {
                    aquired = lock.tryLock(100, TimeUnit.MILLISECONDS);
                    if (aquired) {
                        Thread.sleep(500);
                        Invoice i = Invoice.createRandomInvoice();
                        i.printJustCreated();
                        invoices.add(i);
                        System.out.println(invoices.size());
                        noInvoicesCreated.getAndIncrement();
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    if (aquired)
                        lock.unlock();
                }
            }
        }).start();

        //      *** Generator Pdf ***
        class Pdf extends Thread {
            @Override
            public void run() {
                while (noPdfCreated.get() < 10) {
                    boolean acquired = false;
                    try {
                        acquired = lock.tryLock();
                        if (acquired) {
                            if (!invoices.isEmpty()) {
                                try {
                                    Thread.sleep(5000);
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }
                                System.out.println("Current thread id:" + this.getId());
                                Invoice first = invoices.get(0);
                                invoices.remove(0);
                                first.makeInvoicePdf();
                                invoicesWithPdf.addLast(first);
                                createdInvoicePdf.signal();
                                noPdfCreated.getAndIncrement();
                            }
                        }
                    } finally {
                        if (acquired)
                            lock.unlock();
                    }
                }
            }
        }
        new Pdf().start();
        new Pdf().start();


        //      ***     Wysyłający Pdf      ***
        new Thread(() -> {
            boolean acquired = false;
            while (noEmailsSend.get() < 10) {
                try {
                    acquired = lock.tryLock();
                    if (acquired) {
                        while (invoicesWithPdf.size() == 0) {
                            createdInvoicePdf.await();
                        }
                        invoicesWithPdf.getFirst().sendEmail();
                        invoicesWithPdf.removeFirst();
                        noEmailsSend.getAndIncrement();
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    if (acquired)
                        lock.unlock();
                }
            }
        }).start();
    }
}
