package lab3.skipass.strategy;

import lab3.skipass.Lift;
import lab3.skipass.SkiPass;
import lab3.skipass.Time;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class OnWeekendStrategy implements Strategy {

    @Override
    public SkiPass newSkiPass(LocalDateTime startTime, Lift lift, Time timeType) {
        LocalDateTime expires = startTime.plusDays(timeType.getDurationDays());
        return new SkiPass("Weekend", startTime, expires, lift.getCount(), timeType, this);
    }

    @Override
    public boolean canAccess(SkiPass skiPass, LocalDateTime now) {
        if (skiPass.isBlocked()) return false;

        DayOfWeek day = now.getDayOfWeek();
        boolean isWeekend = (day == DayOfWeek.SATURDAY || day == DayOfWeek.SUNDAY);

        if (!isWeekend || now.isAfter(skiPass.getExpiresAt())) return false;

        LocalTime nowTime = now.toLocalTime();
        return !nowTime.isBefore(skiPass.getTimeType().getStartTime()) &&
                !nowTime.isAfter(skiPass.getTimeType().getEndTime());
    }
}
