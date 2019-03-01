package training.main;

import training.bank.Bank;
import training.bank.BankService;

public class Main {
    public static void main(String args[]) {
        Manager manager = new Manager(new BankService(new Bank()));

        manager.run();
    }
}
