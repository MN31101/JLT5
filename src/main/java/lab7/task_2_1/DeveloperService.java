package lab7.task_2_1;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class DeveloperService {
    public static List<String> getLanguages(List<Developer> team) {
        return team.stream()
                    .map(Developer::getLanguages)
                    .flatMap(Collection::stream)
                    .collect(Collectors.toList());
        }
}
