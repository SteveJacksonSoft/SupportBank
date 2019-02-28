package main;

import commands.Command;
import commands.ListAllCommand;
import commands.ListSpecificAccountCommand;
import commands.QuitCommand;

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
        ui.welcome();
        Command command = ui.getCommand();
        if (command instanceof QuitCommand) {
            System.out.println("Programme exiting.");
            System.exit(-1);
        } else if (command instanceof ListSpecificAccountCommand) {
            this.executeListCommand(((ListSpecificAccountCommand) command).getAccountName());
        } else if (command instanceof ListAllCommand){
            this.executeListAllCommand();
        } else {
            System.out.println("You have not entered a valid command. Please try again.");
            run();
        }
    }

    private void executeListAllCommand() {
        bank.listAll();
    }

    private void executeListCommand(String accountName) {
        try {
            bank.list(accountName);
        } catch (InvalidParameterException e) {
            System.out.println(e.getMessage());
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
