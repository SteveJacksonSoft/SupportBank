package training.bank;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

public class Bank {
    private static final Logger LOGGER = LogManager.getLogger();
    private HashMap<String, Account> accounts = new HashMap<>();
    private List<Transaction> transactions = new ArrayList<>();


    public void updateWithTransactionRecord(TransactionRecord record) {
            this.addNewAccounts(record);
            Transaction newTransaction = this.addTransaction(record);
            newTransaction.getPayee().updateAndPayTransactions(newTransaction);
            newTransaction.getPayer().updateAndPayTransactions(newTransaction);
    }

    private void addNewAccounts(TransactionRecord record) {
        if (!accounts.containsKey(record.getPayee())) {
            accounts.put(record.getPayee(), new Account(record.getPayee()));
        }
        if (!accounts.containsKey(record.getPayer())) {
            accounts.put(record.getPayer(), new Account(record.getPayer()));
        }
    }

    private Transaction addTransaction(TransactionRecord transactionRecord) {
        // Payer and payee should already be in accounts
        Transaction newTransaction = new Transaction(transactionRecord.getDate(),
                accounts.get(transactionRecord.getPayer()),
                accounts.get(transactionRecord.getPayee()),
                transactionRecord.getNarrative(),
                transactionRecord.getAmount()
        );
        transactions.add(newTransaction);
        return newTransaction;
    }

    public Collection<Account> getAllAccounts() {
        return accounts.values();
    }

    public Account getAccount(String accountName) throws InvalidParameterException {
        if (!this.accounts.containsKey(accountName)) {
            throw new InvalidParameterException("No account found with the given owner.");
        }
        return this.accounts.getOrDefault(accountName, null);
    }
}
