package lab7.task_1_1;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class AbbrivationBuilderTest {


    @Test
    public void build_AllParametersNotNull() {
        List<String> list = Arrays.asList("Name", "Petronomics", "Surname");
        assertEquals("NPS", AbbrivationBuilder.build(list));
    }

    @Test
    public void build_OneOfParametersIsNull() throws Exception {
        List<String> list = Arrays.asList("Name", null, "Surname");
        assertEquals("NS", AbbrivationBuilder.build(list));
    }

    @Test
    public void build_OneOfParametersIsEmptyString() throws Exception {
        List<String> list = Arrays.asList("Name", "", "Surname");
        assertEquals("NS", AbbrivationBuilder.build(list));
    }
}