package lab5;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
public class Invoice {
    private long id;
    private List<String> items;
    private List<Integer> counts;
    private List<Double> prices;
    private boolean invoicePdf;
    private Invoice(long id, List<String> items, List<Integer> counts, List<Double> prices) {
        this.id = id;
        this.items = items;
        this.counts = counts;
        this.prices = prices;
    }
    public static Invoice createRandomInvoice() throws InterruptedException {
        Thread.sleep(1000);
        Random r = new Random();
        List<String> availableItems = Arrays.asList("laptop", "smartphone", "HDMI cable", "USB cable",
                "switch", "router", "tablet", "mouse", "keyboard", "USB C to USB adapter",
                "32 GB microSD card", "500 GB external SSD drive");
        List<Double> availablePrices = Arrays.asList(2500.0, 1400.0, 20.0, 10.0, 75.0, 100.0,
                1700.0, 100.0, 80.0, 40.0, 50.0, 650.0);
        int noItems = r.nextInt(5);
        while (noItems == 0) {
            noItems = r.nextInt(5);
        }
        ArrayList<String> selectedItems = new ArrayList<>();
        ArrayList<Integer> selectedCounts = new ArrayList<>();
        ArrayList<Double> selectedPrices = new ArrayList<>();
        for (int i = 0; i < noItems; i++) {
            int randomItemIndex = r.nextInt(availableItems.size());
            selectedItems.add(availableItems.get(randomItemIndex));
            selectedCounts.add(r.nextInt(2));
            selectedPrices.add(availablePrices.get(randomItemIndex));
        }
        return new Invoice(r.nextLong(), selectedItems, selectedCounts, selectedPrices);
    }
    public void printJustCreated() {
        System.out.println("\033[1;32m");
        System.out.println("The new invoice with id " + id + " has been received");
        printInfo();
        System.out.println("\u001B[0m");
    }
    public void makeInvoicePdf() {
        invoicePdf = true;
        System.out.println("\u001B[32m");
        System.out.println("I \"made following invoice pdf\" with id: " + id);
        printInfo();
        System.out.println("\u001B[0m");
    }
    public void sendEmail() {
        System.out.println("\u001B[34m");
        System.out.println("I send email to customer, having following invoice id " + id);
        printInfo();
        System.out.println("\u001B[0m");
    }
    public void printInfo() {
        for (int i = 0; i < items.size(); i++) {
            System.out.println(items.get(i) + "\t" + counts.get(i) + "\t" + prices.get(i));
        }
        System.out.println("pdf created: " + invoicePdf);
    }
    public long getId() {
        return id;
    }
}
