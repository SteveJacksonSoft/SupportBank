package training.bank;

import training.filereading.CSVReader;

import java.io.IOException;
import java.security.InvalidParameterException;
import java.util.HashSet;

public class BankService {
    private final Bank bank;
    private final CSVReader csvReader = new CSVReader();

    public BankService(Bank bank) {
        this.bank = bank;
    }

    public void updateFromRecordsFile(String filePath) throws IOException, NumberFormatException {
        HashSet<TransactionRecord> newTransactionRecords;
        newTransactionRecords = csvReader.importDebtRecords(filePath);
        newTransactionRecords.forEach(bank::updateWithTransactionRecord);
    }

    public void listAccount(String accountName) throws InvalidParameterException {
        Account account = bank.getAccount(accountName);
        if (account == null) {
            throw new InvalidParameterException("No account with that name exists.");
        } else {
            account.printTransactions();
        }
    }

    public void listAllAccounts() {
        bank.payDebts();
        System.out.format("%10s : Balance\n", "Name");
        bank.getAllAccounts().forEach(Account::printBalance);
    }
}
