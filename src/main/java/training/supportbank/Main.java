package training.supportbank;

import java.io.IOException;

public class Main {
    public static void main(String args[]) {
        SupportBank bank = new SupportBank();

        try {
            bank.updateFromRecordsFile("transaction-records/Transactions2014.CSV");
        } catch (IOException e) {
            System.out.println(e.getMessage());
            System.out.println("Any records in the given file will not be added to the database.");
        }

        bank.listAll();
        bank.list("Stephen S");
    }
}
