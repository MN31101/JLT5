package lab9.task1;

public class Bank {
    public void transfer(Account from, Account to, int amount) {
        synchronized (from) {
            synchronized (to) {
                if (from.getBalance() >= amount) {
                    from.withdraw(amount);
                    to.deposit(amount);
                }
            }
        }
    }
}
