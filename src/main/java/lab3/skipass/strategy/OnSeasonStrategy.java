package lab3.skipass.strategy;

import lab3.skipass.Lift;
import lab3.skipass.SkiPass;
import lab3.skipass.Time;

import java.time.LocalDateTime;

public class OnSeasonStrategy implements Strategy {

    @Override
    public SkiPass newSkiPass(LocalDateTime startTime, Lift lift, Time timeType) {
        LocalDateTime expires = startTime.plusDays(timeType.getDurationDays());
        return new SkiPass("Season", startTime, expires, lift.getCount(), timeType, this);
    }

    @Override
    public boolean canAccess(SkiPass skiPass, LocalDateTime now) {
        if (skiPass.isBlocked()) return false;
        return now.isBefore(skiPass.getExpiresAt());
    }
}
