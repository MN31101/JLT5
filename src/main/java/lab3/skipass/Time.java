package lab3.skipass;

import java.time.LocalTime;


public enum Time {
    MORNING(LocalTime.of(9, 0), LocalTime.of(13, 0), 1),
    EVENING(LocalTime.of(13, 0), LocalTime.of(17, 0), 1),
    DAY1(LocalTime.of(9, 0), LocalTime.of(17, 0), 1),
    DAY2(LocalTime.of(9, 0), LocalTime.of(17, 0), 2),
    DAY5(LocalTime.of(9, 0), LocalTime.of(17, 0), 5),
    SEASON(LocalTime.of(9, 0), LocalTime.of(17, 0), 90);

    private final LocalTime startTime;
    private final LocalTime endTime;
    private final int durationDays;

    Time(LocalTime startTime, LocalTime endTime, int durationDays) {
        this.startTime = startTime;
        this.endTime = endTime;
        this.durationDays = durationDays;
    }

    public LocalTime getStartTime() {
        return startTime;
    }

    public LocalTime getEndTime() {
        return endTime;
    }

    public int getDurationDays() {
        return durationDays;
    }

    @Override
    public String toString() {
        return name() + " (" + startTime + "–" + endTime + ", " + durationDays + " days)";
    }
}
