package training.bank;

import java.math.BigDecimal;
import java.security.InvalidParameterException;

public class TransactionRecord {
    private String date;
    private String debtor;
    private String creditor;
    private String narrative;
    private BigDecimal amount;

    public TransactionRecord(String[] args) throws InvalidParameterException, NumberFormatException{
        if (args.length < 5) {
            throw new InvalidParameterException();
        }
        this.date = args[0];
        this.debtor = args[1];
        this.creditor = args[2];
        this.narrative = args[3];
        this.amount = new BigDecimal(args[4]);
    }

    public TransactionRecord(String date, String debtor, String creditor, String narrative, BigDecimal amount) {
//        if (date.matches(""))
        this.date = date;
        this.debtor = debtor;
        this.creditor = creditor;
        this.narrative = narrative;
        this.amount = amount;
    }

    public String getDate() {
        return date;
    }

    public String getNarrative() {
        return narrative;
    }

    public String getDebtor() {
        return debtor;
    }

    public String getCreditor() {
        return creditor;
    }

    public BigDecimal getAmount() {
        return amount;
    }
}
