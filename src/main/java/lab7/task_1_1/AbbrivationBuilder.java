package lab7.task_1_1;

import java.util.List;

public class AbbrivationBuilder {
    public static String build(List<String> list) {
        StringBuilder sb = new StringBuilder();
        for (String s : list) {
            if (s != null && !s.isEmpty()) {
                sb.append(s.charAt(0));
            }
        }
        return sb.toString();
    }
}