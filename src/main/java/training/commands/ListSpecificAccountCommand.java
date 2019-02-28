package training.commands;

public class ListSpecificAccountCommand implements Command {

    private String accountName;

    public ListSpecificAccountCommand(String accountName) {
        this.accountName = accountName;
    }

    public String getAccountName() {
        return accountName;
    }
}
