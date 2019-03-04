package training.bank;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.time.LocalDate;

public class Transaction {
    private LocalDate date;
    private Account payer;
    private Account payee;
    private String purchaseType;
    private BigDecimal value;

    public Transaction(LocalDate date, Account payer, Account payee, String purchaseType, BigDecimal value) {
        this.date = date;
        this.payer = payer;
        this.payee = payee;
        this.purchaseType = purchaseType;
        this.value = value;
    }

    public BigDecimal getValue() {
        return value;
    }

    public Account getPayer() {
        return payer;
    }

    public Account getPayee() {
        return payee;
    }

    public String toString() {
        NumberFormat formatter = NumberFormat.getCurrencyInstance();
        return String.format("%8s | %10s pays %10s for %40s: %s\n",
                date, payer.getOwner(), payee.getOwner(), purchaseType, formatter.format(value));
    }
}
