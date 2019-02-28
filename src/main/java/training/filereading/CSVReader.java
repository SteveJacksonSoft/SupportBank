package training.filereading;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import training.bank.TransactionRecord;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.HashSet;

public class CSVReader {
    private static final Logger LOGGER = LogManager.getLogger();
    // input line format: Date, Debtor, Creditor, purchase type, amount

    public HashSet<TransactionRecord> importDebtRecords(String filePath) throws IOException, NumberFormatException {
        HashSet<TransactionRecord> newTransactionRecords = new HashSet<>();
        ArrayList<String> linesOfInput = this.readFile(filePath);
        for (int i = 1; i < linesOfInput.size(); i++) {
            try {
                newTransactionRecords.add(this.parseRecord(linesOfInput.get(i)));
            } catch (NumberFormatException e) {
                throw new NumberFormatException("There is an invalid transaction value in line " + (i + 1)
                        + " of file: " + filePath);
            }
        }
        if (linesOfInput.size() > 0) {

        }
        return newTransactionRecords;
    }

    private ArrayList<String> readFile(String filePath) throws IOException {
        ArrayList<String> linesOfInput = new ArrayList<>();
        BufferedReader fileReader = new BufferedReader(new FileReader(filePath));
        while (true) {
            String newLine = fileReader.readLine();
            if (newLine == null) {
                break;
            }
            linesOfInput.add(newLine);
        }
        return linesOfInput;
    }

    private TransactionRecord parseRecord(String line) throws InvalidParameterException, NumberFormatException {
        String[] entries = line.split(",");
        return new TransactionRecord(entries);
    }
}
