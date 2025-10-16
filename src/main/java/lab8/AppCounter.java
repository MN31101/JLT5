package lab8;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.ThreadLocalRandom;

public class AppCounter {
    private static final int SIZE = 1_000_000;

    public static void main(String[] args) {
        int[] array = new int[SIZE];
        ForkJoinPool pool = new ForkJoinPool();

        ThreadLocalRandom random = ThreadLocalRandom.current();
        for (int i = 0; i < SIZE; i++) {
           array[i] = random.nextInt(101);
        }

        ForkJoinTask<Integer> task = new ArraySumTask(array, 0, array.length);

        long startTime = System.currentTimeMillis();
        Integer sum = pool.invoke(task);
        long endTime = System.currentTimeMillis();

        System.out.println("Сума елементів масиву: " + sum);
        System.out.println("Час виконання: " + (endTime - startTime) + " ms");
    }

}
