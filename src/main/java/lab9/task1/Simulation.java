package lab9.task1;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

public class Simulation {
    public static void main(String[] args) throws InterruptedException {
        int numAccounts = 5;
        int numThreads = 5;
        ThreadLocalRandom rand = ThreadLocalRandom.current();
        List<Account> accounts = new ArrayList<>();
        Bank bank = new Bank();

        for (int i = 0; i < numAccounts; i++) {
            accounts.add(new Account(i, rand.nextInt(1000)));
        }
        int totalBefore = accounts.stream()
                .mapToInt(Account::getBalance)
                .sum();
        System.out.println("Total before transfers: " + totalBefore);



        ExecutorService executor = Executors.newFixedThreadPool(5);
        for (int i = 0; i < numThreads; i++) {
            executor.submit(() -> {
                Account from = accounts.get(rand.nextInt(numAccounts));
                Account to = accounts.get(rand.nextInt(numAccounts));
                int amount = rand.nextInt(100);
                bank.transfer(from, to, amount);
            });
        }

        executor.shutdown();
        executor.awaitTermination(1000, TimeUnit.MILLISECONDS);

        int totalAfter = accounts.stream()
                .mapToInt(Account::getBalance)
                .sum();
        System.out.println("Total after transfers: " + totalAfter);

    }
}
