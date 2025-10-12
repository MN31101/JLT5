package lab3;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

public class Turnstile {

    private static class AccessRecord {
        final SkiPass pass;
        final boolean allowed;
        final LocalDateTime timestamp;

        AccessRecord(SkiPass pass, boolean allowed, LocalDateTime timestamp) {
            this.pass = pass;
            this.allowed = allowed;
            this.timestamp = timestamp;
        }
    }

    private final List<AccessRecord> accessLog = new ArrayList<>();

    public boolean tryPass(SkiPass pass) {
        LocalDateTime now = LocalDateTime.now();

        if (pass == null) {
            System.out.println("Undefined");
            return log(pass, false, now);
        }
        if (pass.isBlocked()) {
            System.out.println("Card blocked " + pass.getId());
            return log(pass, false, now);
        }
        if (now.isAfter(pass.getExpiresAt())) {
            System.out.println("Card expired " + pass.getId());
            return log(pass, false, now);
        }

        boolean canAccess = pass.canAccess(now);
        if (!canAccess) {
            System.out.println("Time is up: " + pass.getType());
            return log(pass, false, now);
        }

        if (pass.getRemainingLifts() == 0) {
            System.out.println("Attempts exhausted: " + pass.getId());
            return log(pass, false, now);
        }

        pass.useLift();
        System.out.println("Granted for: " + pass.getType());
        return log(pass, true, now);
    }

    private boolean log(SkiPass pass, boolean allowed, LocalDateTime timestamp) {
        accessLog.add(new AccessRecord(pass, allowed, timestamp));
        return allowed;
    }

    public String getData() {
        long granted = accessLog.stream().filter(r -> r.allowed).count();
        long denied = accessLog.size() - granted;

        return "General statistic:\n" +
                "  • Total size: " + accessLog.size() + "\n" +
                "  • Granted: " + granted + "\n" +
                "  • Denied: " + denied + "\n";
    }

    public String getDataByType() {
        Map<String, List<Boolean>> byType = accessLog.stream()
                .filter(r -> r.pass != null)
                .collect(Collectors.groupingBy(
                        r -> r.pass.getType(),
                        Collectors.mapping(r -> r.allowed, Collectors.toList())
                ));

        StringBuilder sb = new StringBuilder("Type statistic: \n");
        for (var entry : byType.entrySet()) {
            long total = entry.getValue().size();
            long granted = entry.getValue().stream().filter(v -> v).count();
            long denied = total - granted;
            sb.append("  • ").append(entry.getKey())
                    .append(": granted ").append(granted)
                    .append(", denied ").append(denied)
                    .append("\n");
        }

        return sb.toString();
    }

    public void clearLog() {
        accessLog.clear();
    }

    public List<AccessRecord> getAccessLog() {
        return new ArrayList<>(accessLog);
    }
}