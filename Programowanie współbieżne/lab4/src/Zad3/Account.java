package lab4.zad3;

public class Account {
    double debit, actualSalary;

    public Account(double debit, double actualSalary) {
        this.debit = debit;
        this.actualSalary = actualSalary;
    }
    synchronized void subtract(double amount) {
        if (actualSalary - amount >= debit) {
            System.out.print(actualSalary + "-" + amount + "=");
            actualSalary -= amount;
            System.out.println(actualSalary);
        } else {
            System.out.print(actualSalary + "-" + amount + " < dopuszczalnego debetu");
            System.out.println(", nie da się wykonać transakcji!!!");
        }
    }
    synchronized void add(double amount) {
        actualSalary += amount;
    }
    synchronized double getActualSalary() {
        return actualSalary;
    }
}
