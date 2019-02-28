package training.main;

import training.commands.Command;
import training.commands.ListAllCommand;
import training.commands.ListSpecificAccountCommand;
import training.commands.QuitCommand;

import java.util.Scanner;

public class UserInterface {
    private final Scanner scanner = new Scanner(System.in);

    public void welcome() {
        System.out.println("Welcome to the support bank.");
        System.out.println("============================");
    }

    public Command getCommand() {
        String input = promptForCommand();
        return processCommand(input);
    }

    public boolean userWantsNewAction() {
        System.out.print("Would you like to execute a new command (y/n)? > ");
        String input = scanner.nextLine().trim();
        if (input.equals("y")) {
            return true;
        } else if (input.equals("n")) {
            return false;
        } else {
            System.out.println("I'll take that as a no.");
            return false;
        }
    }

    private String promptForCommand() {
        System.out.println("Commands:");
        System.out.println("'q' = [quit]");
        System.out.println("'list *name*' = [prints list of transactions involving the account owned by *name*]");
        System.out.println("'list all' = [prints balances of all accounts in the bank]");
        System.out.print("Please enter a command. > ");

        return scanner.nextLine();
    }

    private Command processCommand(String input) {
        input = input.toLowerCase().trim();

        if (input.equals("q")) {
            return new QuitCommand();
        }

        return parseListCommand(input);
    }

    private Command parseListCommand(String input) {
        input = input.toLowerCase().trim();
        if (input.matches("\\w+\\s+\\w.*")) {
            String[] words = input.split("\\s+");
            if (words[0].equals("list")) {
                if (words.length == 2 && words[1].equals("all")){
                    return new ListAllCommand();
                } else {
                    return new ListSpecificAccountCommand(input.substring(4).trim());
                }
            }
        }
        return null;
    }
}
