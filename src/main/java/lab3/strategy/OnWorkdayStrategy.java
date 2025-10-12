package lab3.strategy;

import lab3.constants.Lift;
import lab3.SkiPass;
import lab3.constants.Time;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class OnWorkdayStrategy implements Strategy {

    @Override
    public SkiPass newSkiPass(LocalDateTime startTime, Lift lift, Time timeType) {
        LocalDateTime expires = startTime.plusDays(timeType.getDurationDays());
        return new SkiPass("Workday", startTime, expires, lift.getCount(), timeType, this);
    }

    @Override
    public boolean canAccess(SkiPass skiPass, LocalDateTime now) {
        if (skiPass.isBlocked()) return false;

        DayOfWeek day = now.getDayOfWeek();
        boolean isWorkday = day != DayOfWeek.SATURDAY && day != DayOfWeek.SUNDAY;
        if (!isWorkday) return false;

        if (now.isBefore(skiPass.getStartedAt()) || now.isAfter(skiPass.getExpiresAt())) {
            return false;
        }
        LocalTime nowTime = now.toLocalTime();
        return !nowTime.isBefore(skiPass.getTimeType().getStartTime()) &&
                !nowTime.isAfter(skiPass.getTimeType().getEndTime());
    }
}
