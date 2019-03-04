package training.bank;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import training.filereading.TransactionRecordImporter;

import java.io.IOException;
import java.security.InvalidParameterException;
import java.util.Collection;
import java.util.List;

public class BankService {
    private static final Logger LOGGER = LogManager.getLogger();
    private final Bank bank;
    private final TransactionRecordImporter recordImporter = new TransactionRecordImporter();

    public BankService(Bank bank) {
        this.bank = bank;
    }

    public void updateFromRecordsFile(String filePath) throws IOException, NumberFormatException {
        List<TransactionRecord> newTransactionRecords;
        newTransactionRecords = recordImporter.importTransactionRecords(filePath);
        newTransactionRecords.forEach(bank::updateWithTransactionRecord);
    }

    public List<Transaction> getAccountTransactions(String accountName) throws InvalidParameterException {
        Account account = bank.getAccount(accountName);
        if (account == null) {
            LOGGER.warn("Trying to list account [" + accountName + "] but the account name is not in the bank database.");
            throw new InvalidParameterException("No account with that name exists.");
        } else {
            LOGGER.debug("Printing transactions for account [" + accountName + "].");
            return account.getTransactions();
        }
    }

    public Collection<Account> getAllAccounts() {
        return bank.getAllAccounts();
    }

}
