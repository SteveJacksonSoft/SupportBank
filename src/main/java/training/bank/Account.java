package training.bank;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;

public class Account {
    private String owner;
    private BigDecimal balance = BigDecimal.ZERO;
    private HashSet<Transaction> debts = new HashSet<>();
    private HashSet<Transaction> credits = new HashSet<>();

    public Account(String owner) {
        this.owner = owner;
    }

    private void increaseBalance(BigDecimal amount) {
        balance = balance.add(amount);
    }

    private void decreaseBalance(BigDecimal amount) {
        balance = balance.subtract(amount);
    }

    public String getOwner() {
        return owner;
    }

    public void updateAndPayTransactions(Transaction transaction) { // Is this adding unnecessary complexity?
        if (transaction.getPayer().equals(this)) {
            this.debts.add(transaction);
            this.decreaseBalance(transaction.getValue());
        } else if (transaction.getPayee().equals(this)) {
            this.credits.add(transaction);
            this.increaseBalance((transaction.getValue()));
        }
    }

    public String writeBalance() {
        NumberFormat format = NumberFormat.getCurrencyInstance();
        return String.format("%10s: %s\n", this.owner, format.format(this.balance));
    }

    public List<Transaction> getTransactions() {
        List<Transaction> transactionStrings = new ArrayList<>();
        transactionStrings.addAll(this.debts);
        transactionStrings.addAll(this.credits);
        return transactionStrings;
    }
}
