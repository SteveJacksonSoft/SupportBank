package training.bank;

import java.math.BigDecimal;
import java.security.InvalidParameterException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class TransactionRecord {
    private final DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    private LocalDate date;
    private String payer;
    private String payee;
    private String narrative;
    private BigDecimal amount;

    public TransactionRecord(String[] args) throws InvalidParameterException, NumberFormatException, DateTimeParseException {

        if (args.length < 5) {
            throw new InvalidParameterException();
        }
        this.date = LocalDate.parse(args[0], dateFormat);
        this.payer = args[1].toLowerCase();
        this.payee = args[2].toLowerCase();
        this.narrative = args[3];
        this.amount = new BigDecimal(args[4]);
    }

    public TransactionRecord(LocalDate date, String payer, String payee, String narrative, BigDecimal amount) {
        this.date = date;
        this.payer = payer.toLowerCase();
        this.payee = payee.toLowerCase();
        this.narrative = narrative;
        this.amount = amount;
    }

    public String getDateAsString() {
        return date.toString();
    }

    public LocalDate getDate() {
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
