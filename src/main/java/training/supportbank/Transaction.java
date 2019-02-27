package training.supportbank;

public class Transaction {
    private String date;
    private Account payer;
    private Account payee;
    private String purchaseType;
    private double value;

    private boolean isPaid = false;

    public Transaction(String date, Account payer, Account payee, String purchaseType, double value) {
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
