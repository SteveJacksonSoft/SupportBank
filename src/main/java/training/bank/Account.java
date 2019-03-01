package training.bank;

import java.lang.reflect.Array;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class Account {
    private String owner;
    private BigDecimal balance = BigDecimal.ZERO;
    private HashSet<Transaction> debts = new HashSet<>();
    private HashSet<Transaction> credits = new HashSet<>();

    public Account(String owner) {
        this.owner = owner;
    }

    public void increaseBalance(BigDecimal amount) {
        balance = balance.add(amount);
    }

    public void decreaseBalance(BigDecimal amount) {
        balance = balance.subtract(amount);
    }

    public String getOwner() {
        return owner;
    }

    public BigDecimal getBalance() {
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

    public String writeBalance() {
        return String.format("%10s: £%.2f\n", owner, balance);
    }

    public List<Transaction> getTransactions() {
        this.payTransactions();
        ArrayList<Transaction> transactionStrings = new ArrayList<>();
        transactionStrings.addAll(this.debts);
        transactionStrings.addAll(this.credits);
        return transactionStrings;
    }

    private void payTransactions() {
        debts.forEach(Transaction::pay);
        credits.forEach(Transaction::pay);
    }
}
