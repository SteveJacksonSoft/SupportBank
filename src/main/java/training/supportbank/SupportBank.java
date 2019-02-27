package training.supportbank;

import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;

public class SupportBank {
    private CSVReader csvReader = new CSVReader();
    private HashMap<String, Account> accounts = new HashMap<>(); // Is this better than HashSet?
    private HashSet<Transaction> transactions = new HashSet<>();

    public void listAll() {
        this.payDebts();
        System.out.format("%10s : Balance\n", "Name");
        accounts.forEach((accountOwner, account) -> account.printBalance());
    }

    public void list(String accountOwner) {
        accounts.get(accountOwner).printTransactions();
    }

    public void payDebts() {
        transactions.forEach(Transaction::pay);
    }

    public void updateFromRecordsFile(String filePath) throws IOException {
        HashSet<TransactionRecord> newTransactionRecords = csvReader.importDebtRecords(filePath);
        addNewAccounts(newTransactionRecords);
        newTransactionRecords.forEach(transactionRecord -> {
            Transaction newTransaction = addTransaction(transactionRecord);
            newTransaction.getPayee().updateTransactions(newTransaction);
            newTransaction.getPayer().updateTransactions(newTransaction);
        });
    }

    private void addNewAccounts(HashSet<TransactionRecord> newTransactions) {
        newTransactions.forEach(debt -> {
            if (!accounts.containsKey(debt.getCreditor())) {
                accounts.put(debt.getCreditor(), new Account(debt.getCreditor()));
            }
            if (!accounts.containsKey(debt.getDebtor())) {
                accounts.put(debt.getDebtor(), new Account(debt.getDebtor()));
            }
        });
    }

    private Transaction addTransaction(TransactionRecord transactionRecord) { // Check implementation of HS.addAll for efficiency
        String date = transactionRecord.getDate();
        Account debtor = accounts.get(transactionRecord.getDebtor());
        Account creditor = accounts.get(transactionRecord.getCreditor()); // Is it bad practice to assume that debtor and creditor are already in accounts?
        String purchaseType = transactionRecord.getPurchaseType();
        double amount = transactionRecord.getAmount();

        Transaction newTransaction = new Transaction( date, debtor, creditor, purchaseType, amount);
        transactions.add(newTransaction);
        return newTransaction;
    }
}
