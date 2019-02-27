package training.supportbank;

import java.io.IOException;
import java.security.InvalidParameterException;

public class Manager {
    private final UserInterface ui = new UserInterface();
    private final String[] recordFiles = {
            "transaction-records/Transactions2014.csv"
    };

    private SupportBank bank;

    public Manager(SupportBank bank) {
        this.bank = bank;
    }

    public void run() {
        this.updateBank();
        Command command = ui.getCommand();
        if (command instanceof QuitCommand) {
            System.out.println("Programme exiting.");
            System.exit(-1);
        } else if (command instanceof ListSpecificAccountCommand) {
            try {
                bank.list(((ListSpecificAccountCommand) command).getAccountName());
            } catch (InvalidParameterException e) {
                System.out.println(e.getMessage());
                run();
            }
        } else if (command instanceof ListAllCommand){
            bank.listAll();
        } else {
            System.out.println("You have not entered a valid command. Please try again.");
            run();
        }
    }


    private void updateBank() {
        for (String filePath: recordFiles) {
            try {
                bank.updateFromRecordsFile(filePath);
            } catch (IOException e) {
                System.out.println(e.getMessage());
                System.out.println("Any records in the given file will not be added to the database.");
            }
        }
    }
}
