package training.filereading;

import training.bank.TransactionRecord;

import java.io.IOException;
import java.util.HashSet;

public interface Reader {

    HashSet<TransactionRecord> importTransactionRecords(String filePath) throws IOException, NumberFormatException;
}
