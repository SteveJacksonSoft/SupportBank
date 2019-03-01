package training.bank;

import java.math.BigDecimal;
import java.time.LocalDate;

public class Transaction {
    private LocalDate date;
    private Account payer;
    private Account payee;
    private String purchaseType;
    private BigDecimal value;

    private boolean isPaid = false;

    public Transaction(LocalDate date, Account payer, Account payee, String purchaseType, BigDecimal value) {
        this.date = date;
        this.payer = payer;
        this.payee = payee;
        this.purchaseType = purchaseType;
        this.value = value;
    }

    public void pay() {
        if (!this.isPaid) {
            payer.decreaseBalance(value);
            payee.increaseBalance(value);
            this.isPaid = true;
        }
    }

    public Account getPayer() {
        return payer;
    }

    public Account getPayee() {
        return payee;
    }

    public void print() {
        System.out.format("%8s | %10s pays %10s for %20s : £%.2f\n", date, payer.getOwner(), payee.getOwner(), purchaseType, value);
    }
}
