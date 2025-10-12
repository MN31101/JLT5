package lab3;

import lab3.constants.Time;
import lab3.strategy.Strategy;

import java.time.LocalDateTime;
import java.util.UUID;

public class SkiPass {
    private final UUID id;
    private final String type;
    private boolean blocked;
    private final LocalDateTime startedAt;
    private final LocalDateTime expiresAt;
    private int remainingLifts;
    private final Strategy strategy;
    private final Time timeType;

    public SkiPass(String type, LocalDateTime startedAt, LocalDateTime expiresAt, int remainingLifts, Time timeType, Strategy strategy) {
        this.id = UUID.randomUUID();
        this.type = type;
        this.startedAt = startedAt;
        this.expiresAt = expiresAt;
        this.remainingLifts = remainingLifts;
        this.strategy = strategy;
        this.timeType = timeType;
    }

    public boolean canAccess(LocalDateTime now) {
        return strategy.canAccess(this, now);
    }

    public boolean useLift() {
        if (remainingLifts > 0) {
            remainingLifts--;
            return true;
        }
        return false;
    }

    public UUID getId() {
        return id;
    }

    public String getType() {
        return type;
    }

    public LocalDateTime getStartedAt() {
        return startedAt;
    }

    public LocalDateTime getExpiresAt() {
        return expiresAt;
    }

    public int getRemainingLifts() {
        return remainingLifts;
    }

    public boolean isBlocked() {
        return blocked;
    }

    public void block() {
        this.blocked = true;
    }

    public Strategy getStrategy() {
        return strategy;
    }

    public Time getTimeType() {
        return timeType;
    }

    @Override
    public String toString() {
        return "SkiPass{" +
                "id=" + id +
                ", type='" + type + '\'' +
                ", blocked=" + blocked +
                ", startedAt=" + startedAt +
                ", expiresAt=" + expiresAt +
                ", remainingLifts=" + remainingLifts +
                ", timeType=" + timeType +
                '}';
    }
}
