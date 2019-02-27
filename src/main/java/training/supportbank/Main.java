package training.supportbank;

public class Main {
    public static void main(String args[]) {
        Manager manager = new Manager(new SupportBank());

        manager.run();
    }
}
