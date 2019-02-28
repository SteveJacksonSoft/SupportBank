package training.main;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import training.bank.Bank;
import training.bank.BankService;
import training.commands.Command;
import training.commands.ListAllCommand;
import training.commands.ListSpecificAccountCommand;
import training.commands.QuitCommand;

import java.security.InvalidParameterException;

public class Manager {
    private static final Logger LOGGER = LogManager.getLogger();
    private final UserInterface ui = new UserInterface();
    private final String[] recordFiles = {
            "transaction-records/Transactions2014.csv",
            "transaction-records/Transactions2015.csv"
    };

    private BankService bankService;

    public Manager(BankService bankService) {
        this.bankService = bankService;
    }

    public void run() {
        this.updateBank();
        ui.welcome();
        this.carryOutService();
    }

    private void updateBank() {
        for (String filePath: recordFiles) {
            try {
                bankService.updateFromRecordsFile(filePath);
            } catch (Exception e) {
                System.out.println(e.getMessage());
                System.out.println("Any records in the given file will not be added to the database.");
            }
        }
    }

    private void carryOutService() {
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
            this.carryOutService();
        }
        if (ui.userWantsNewAction()) {
            this.carryOutService();
        }
    }

    private void executeListAllCommand() {
        bankService.listAllAccounts();
    }

    private void executeListCommand(String accountName) {
        try {
            bankService.listAccount(accountName);
        } catch (InvalidParameterException e) {
            System.out.println(e.getMessage());
            run();
        }
    }
}
