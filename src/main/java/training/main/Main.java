package training.main;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import training.bank.SupportBank;

public class Main {
    private static final Logger LOGGER = LogManager.getLogger();
    public static void main(String args[]) {
        Manager manager = new Manager(new SupportBank());

        manager.run();
    }
}
