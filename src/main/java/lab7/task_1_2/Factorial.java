package lab7.task_1_2;

import java.util.stream.LongStream;

public class Factorial {
    public static long factorial(long i) {
        return LongStream.rangeClosed(1, i)
                .reduce(1, (a, b) -> a * b);
    }
}