package training.supportbank;

import java.util.HashSet;

public class Account {
    private String owner;
    private double balance;
    private HashSet<Transaction> debts = new HashSet<>();
    private HashSet<Transaction> credits = new HashSet<>();

    public Account(String owner) {
        this.owner = owner;
    }

    public void increaseBalance(double amount) {
        balance += amount;
    }

    public void decreaseBalance(double amount) {
        balance -= amount;
    }

    public String getOwner() {
        return owner;
    }

    public double getBalance() {
        this.payTransactions();
        return balance;
    }

    public void updateTransactions(Transaction transaction) { // Is this adding unnecessary complexity?
        if (transaction.getPayer().equals(this)) {
            this.debts.add(transaction);
        } else if (transaction.getPayee().equals(this)) {
            this.credits.add(transaction);
        }
    }

    public void printBalance() {
        System.out.format("%10s: £%.2f\n", owner, balance);
    }

    public void printTransactions() {
        this.payTransactions();
        debts.forEach(Transaction::print);
        credits.forEach(Transaction::print);
    }

    private void payTransactions() {
        debts.forEach(Transaction::pay);
        credits.forEach(Transaction::pay);
    }
}
