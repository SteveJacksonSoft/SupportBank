package training.supportbank;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.security.InvalidParameterException;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.HashSet;

public class CSVReader {
    // input line format: Date, Debtor, Creditor, purchase type, amount

    public HashSet<TransactionRecord> importDebtRecords(String filePath) throws IOException {
        HashSet<TransactionRecord> newTransactionRecords = new HashSet<>();
        ArrayList<String> linesOfInput = this.readFile(filePath);
        if (linesOfInput.size() > 0) {
            linesOfInput.remove(0); // Title line of file
            linesOfInput.forEach(line -> {
                newTransactionRecords.add(this.parseRecord(line));
            });
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

    private TransactionRecord parseRecord(String line) throws InvalidParameterException {

        String[] entries = line.split(",");
        return new TransactionRecord(entries);
    }
}
