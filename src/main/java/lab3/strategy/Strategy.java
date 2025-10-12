package lab3.strategy;

import lab3.constants.Lift;
import lab3.SkiPass;
import lab3.constants.Time;

import java.time.LocalDateTime;

public interface Strategy {
    SkiPass newSkiPass(LocalDateTime startTime, Lift lift, Time timeType) ;
    boolean canAccess(SkiPass skiPass, LocalDateTime now);
}
