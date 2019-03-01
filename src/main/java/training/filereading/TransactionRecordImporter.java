package training.filereading;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import training.bank.TransactionRecord;

import java.io.IOException;
import java.time.format.DateTimeParseException;
import java.util.HashSet;

public class TransactionRecordImporter {
    private static final Logger LOGGER = LogManager.getLogger();
    private final CSVReader csvReader = new CSVReader();
    private  final JSONRecordReader jsonRecordReader = new JSONRecordReader();

    public HashSet<TransactionRecord> importTransactionRecords(String filePath) throws IOException, NumberFormatException, DateTimeParseException {
        Reader reader = this.chooseReader(filePath);
        if (reader == null) {
            throw new IOException("No file extension is given which this program recognises.");
        }
        return reader.getRecordsFromFile(filePath);
    }

    private Reader chooseReader(String filePath) {
        if (filePath.endsWith(".csv")) {
            return this.csvReader;
        } else if (filePath.endsWith(".json")) {
            return this.jsonRecordReader;
        } else {
            return null;
        }
    }
}
