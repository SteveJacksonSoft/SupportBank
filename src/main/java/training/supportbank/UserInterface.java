package training.supportbank;

import java.util.Scanner;

public class UserInterface {
    private final Scanner scanner = new Scanner(System.in);

    public void welcome() {
        System.out.println("Welcome to the support bank.");
    }

    public Command getCommand() {
        String input = promptForCommand();
        return processCommand(input);
    }

    public String promptForCommand() {
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
