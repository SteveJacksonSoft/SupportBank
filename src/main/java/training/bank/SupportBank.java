package training.bank;

import training.filereading.CSVReader;

import java.io.IOException;
import java.math.BigDecimal;
import java.security.InvalidParameterException;
import java.util.HashMap;
import java.util.HashSet;

public class SupportBank {
    private CSVReader csvReader = new CSVReader();
    private HashMap<String, Account> accounts = new HashMap<>(); // Is this better than HashSet?
    private HashSet<Transaction> transactions = new HashSet<>();

    public void updateFromRecordsFile(String filePath) throws IOException {
        HashSet<TransactionRecord> newTransactionRecords = csvReader.importDebtRecords(filePath);
        addNewAccounts(newTransactionRecords);
        newTransactionRecords.forEach(transactionRecord -> {
            Transaction newTransaction = addTransaction(transactionRecord);
            newTransaction.getPayee().updateTransactions(newTransaction);
            newTransaction.getPayer().updateTransactions(newTransaction);
        });
    }

    private void addNewAccounts(HashSet<TransactionRecord> newTransactionRecords) {
        newTransactionRecords.forEach(transactionRecord -> {
            if (!accounts.containsKey(transactionRecord.getCreditor())) {
                accounts.put(transactionRecord.getCreditor(), new Account(transactionRecord.getCreditor()));
            }
            if (!accounts.containsKey(transactionRecord.getDebtor())) {
                accounts.put(transactionRecord.getDebtor(), new Account(transactionRecord.getDebtor()));
            }
        });
    }

    private Transaction addTransaction(TransactionRecord transactionRecord) { // Check implementation of HS.addAll for efficiency
        String date = transactionRecord.getDate();
        Account debtor = accounts.get(transactionRecord.getDebtor());
        Account creditor = accounts.get(transactionRecord.getCreditor()); // Is it bad practice to assume that debtor and creditor are already in accounts?
        String purchaseType = transactionRecord.getNarrative();
        BigDecimal amount = transactionRecord.getAmount();

        Transaction newTransaction = new Transaction( date, debtor, creditor, purchaseType, amount);
        transactions.add(newTransaction);
        return newTransaction;
    }

    public void listAll() {
        this.payDebts();
        System.out.format("%10s : Balance\n", "Name");
        accounts.forEach((accountOwner, account) -> account.printBalance());
    }

    public void list(String accountOwner) throws InvalidParameterException {
        if (!accounts.containsKey(accountOwner)) {
            throw new InvalidParameterException("No account found with the given owner.");
        }
        accounts.get(accountOwner).printTransactions();
    }

    public void payDebts() {
        transactions.forEach(Transaction::pay);
    }
}
