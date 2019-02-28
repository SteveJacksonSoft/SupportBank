package training.bank;

import java.math.BigDecimal;
import java.security.InvalidParameterException;

public class TransactionRecord {
    private String date;
    private String payer;
    private String payee;
    private String narrative;
    private BigDecimal amount;

    public TransactionRecord(String[] args) throws InvalidParameterException, NumberFormatException{
        if (args.length < 5) {
            throw new InvalidParameterException();
        }
        this.date = args[0];
        this.payer = args[1].toLowerCase();
        this.payee = args[2].toLowerCase();
        this.narrative = args[3];
        this.amount = new BigDecimal(args[4]);
    }

    public TransactionRecord(String date, String payer, String payee, String narrative, BigDecimal amount) {
//        if (date.matches(""))
        this.date = date;
        this.payer = payer.toLowerCase();
        this.payee = payee.toLowerCase();
        this.narrative = narrative;
        this.amount = amount;
    }

    public String getDate() {
        return date;
    }

    public String getNarrative() {
        return narrative;
    }

    public String getPayer() {
        return payer;
    }

    public String getPayee() {
        return payee;
    }

    public BigDecimal getAmount() {
        return amount;
    }
}
