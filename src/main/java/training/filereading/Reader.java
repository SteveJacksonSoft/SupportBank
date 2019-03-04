package training.filereading;

import training.bank.TransactionRecord;

import java.io.IOException;
import java.util.List;

public interface Reader {

    List<TransactionRecord> getRecordsFromFile(String filePath) throws IOException, NumberFormatException;

}
