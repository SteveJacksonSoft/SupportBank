package training.filereading;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import training.bank.TransactionRecord;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.security.InvalidParameterException;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;

public class CSVReader implements Reader {
    private static final Logger LOGGER = LogManager.getLogger();
    // input line format: Date, Debtor, Creditor, purchase type, amount

    public List<TransactionRecord> getRecordsFromFile(String filePath) throws IOException, NumberFormatException {
        List<String> linesOfInput = this.readFile(filePath);
        List<TransactionRecord> newTransactionRecords = new ArrayList<>();

        for (int index = 1; index < linesOfInput.size(); index++) {
            try {
                LOGGER.debug("Adding new transaction record from line " + index + " of file: " + filePath);
                newTransactionRecords.add(this.parseRecord(linesOfInput.get(index)));
                LOGGER.debug("Transaction record from line " + index + " of file [" + filePath + "added successfully");
            } catch (NumberFormatException e) {
                LOGGER.error("NumberFormatException thrown - there is an invalid transaction value in line " + (index + 1)
                        + " of file: " + filePath);
                throw new NumberFormatException("There is an invalid transaction value in line " + (index + 1)
                        + " of file: " + filePath);
            } catch (DateTimeParseException e) {
                LOGGER.error("NumberFormatException thrown - there is an invalid transaction date in line " + (index + 1)
                        + " of file: " + filePath);
                throw new NumberFormatException("There is an invalid transaction date in line " + (index + 1)
                        + " of file: " + filePath);
            }
        }

        return newTransactionRecords;
    }

    private ArrayList<String> readFile(String filePath) throws IOException {
        LOGGER.info("Reading file: " + filePath);
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
        LOGGER.debug("Creating new transaction record instance from array:");
        LOGGER.debug(line);
        return new TransactionRecord(entries);
    }
}
