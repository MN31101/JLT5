package lab7.task_1_2;


import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class FactorialTest {

    @Test
    public void factorial_EdgeParameter() {
        long i = 1;
        assertEquals(1, Factorial.factorial(i));

    }

    @Test
    public void factorial_NormalParameter() {
        long i = 5;
        assertEquals(120, Factorial.factorial(i));
    }

}