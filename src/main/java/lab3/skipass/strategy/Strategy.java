package lab3.skipass.strategy;

import lab3.skipass.Lift;
import lab3.skipass.SkiPass;
import lab3.skipass.Time;

import java.time.LocalDateTime;

public interface Strategy {
    SkiPass newSkiPass(LocalDateTime startTime, Lift lift, Time timeType) ;
    boolean canAccess(SkiPass skiPass, LocalDateTime now);
}
