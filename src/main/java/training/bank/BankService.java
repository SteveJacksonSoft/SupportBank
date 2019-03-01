package training.bank;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import training.filereading.CSVReader;

import java.io.IOException;
import java.security.InvalidParameterException;
import java.util.HashSet;

public class BankService {
    private static final Logger LOGGER = LogManager.getLogger();
    private final Bank bank;
    private final CSVReader csvReader = new CSVReader();

    public BankService(Bank bank) {
        this.bank = bank;
    }

    public void updateFromRecordsFile(String filePath) throws IOException, NumberFormatException {
        HashSet<TransactionRecord> newTransactionRecords;
        newTransactionRecords = csvReader.importTransactionRecords(filePath);
        newTransactionRecords.forEach(bank::updateWithTransactionRecord);
    }

    public void listAccount(String accountName) throws InvalidParameterException {
        Account account = bank.getAccount(accountName);
        if (account == null) {
            LOGGER.warn("Trying to list account [" + accountName + "] but the account name is not in the bank database.");
            throw new InvalidParameterException("No account with that name exists.");
        } else {
            LOGGER.debug("Printing transactions for account [" + accountName + "].");
            account.printTransactions();
        }
    }

    public void listAllAccounts() {
        bank.payDebts();
        System.out.format("%10s : Balance\n", "Name");
        LOGGER.debug("Listing all accounts.");
        bank.getAllAccounts().forEach(Account::printBalance);
    }
}
