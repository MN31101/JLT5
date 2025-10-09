package lab3;

import lab3.skipass.Lift;
import lab3.skipass.SkiPass;
import lab3.skipass.Time;
import lab3.skipass.strategy.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.temporal.TemporalAdjusters;

import static org.junit.jupiter.api.Assertions.*;

public class TurnstileTest {

    private Turnstile turnstile;

    @BeforeEach
    void setUp() {
        turnstile = new Turnstile();
    }

    @Test
    void testSeasonPassAccess() {
        Strategy strategy = new OnSeasonStrategy();
        SkiPass seasonPass = strategy.newSkiPass(LocalDateTime.now(), Lift.INFINITE, Time.SEASON);

        boolean accessGranted = turnstile.tryPass(seasonPass);
        assertTrue(accessGranted, "Season pass should allow access");
    }

    @Test
    void testExpiredSeasonPass() {
        Strategy strategy = new OnSeasonStrategy();

        SkiPass expiredPass = new SkiPass(
                "SEASON",
                LocalDateTime.now().minusDays(91),
                LocalDateTime.now().minusDays(1),
                Integer.MAX_VALUE,
                Time.SEASON,
                strategy
        );

        boolean accessDenied = turnstile.tryPass(expiredPass);
        assertFalse(accessDenied, "Expired pass should deny access");
    }

    @Test
    void testWorkdayMorningPassAllowed() {
        Strategy strategy = new OnWorkdayStrategy();

        LocalDateTime nextWorkday = getNextWorkday();

        SkiPass morningPass = strategy.newSkiPass(nextWorkday, Lift.TEN, Time.MORNING);

        LocalDateTime testTime = nextWorkday.withHour(10).withMinute(0);
        boolean allowed = strategy.canAccess(morningPass, testTime);

        assertTrue(allowed, "Workday MORNING pass should allow access during 9-13");
    }

    @Test
    void testWorkdayEveningPassDeniedInMorning() {
        Strategy strategy = new OnWorkdayStrategy();

        LocalDateTime nextWorkday = getNextWorkday();

        SkiPass eveningPass = strategy.newSkiPass(nextWorkday, Lift.TEN, Time.EVENING);

        LocalDateTime testTime = nextWorkday.withHour(10).withMinute(0);
        boolean denied = strategy.canAccess(eveningPass, testTime);

        assertFalse(denied, "Workday EVENING pass should deny access during morning hours");
    }

    @Test
    void testWeekendPassAllowedOnSaturday() {
        Strategy strategy = new OnWeekendStrategy();

        LocalDateTime saturday = LocalDateTime.now()
                .with(TemporalAdjusters.nextOrSame(DayOfWeek.SATURDAY))
                .withHour(11)
                .withMinute(0);

        SkiPass weekendPass = strategy.newSkiPass(saturday, Lift.TEN, Time.DAY1);

        boolean allowed = strategy.canAccess(weekendPass, saturday);
        assertTrue(allowed, "Weekend pass should allow access on Saturday");
    }

    @Test
    void testWeekendPassDeniedOnTuesday() {
        Strategy strategy = new OnWeekendStrategy();

        LocalDateTime saturday = LocalDateTime.now()
                .with(TemporalAdjusters.nextOrSame(DayOfWeek.SATURDAY));

        SkiPass weekendPass = strategy.newSkiPass(saturday, Lift.TEN, Time.DAY1);

        LocalDateTime tuesday = LocalDateTime.now()
                .with(TemporalAdjusters.nextOrSame(DayOfWeek.TUESDAY))
                .withHour(11)
                .withMinute(0);

        boolean denied = strategy.canAccess(weekendPass, tuesday);
        assertFalse(denied, "Weekend pass should deny access on Tuesday");
    }

    @Test
    void testLiftCountDecreasesAfterAccess() {
        Strategy strategy = new OnWorkdayStrategy();

        LocalDateTime nextWorkday = getNextWorkday().withHour(10).withMinute(0);

        SkiPass liftPass = new SkiPass(
                "WORKDAY",
                nextWorkday,
                nextWorkday.plusDays(1),
                2,
                Time.DAY1,
                strategy
        );

        assertTrue(turnstile.tryPass(liftPass), "First pass should be granted");
        assertEquals(1, liftPass.getRemainingLifts(), "Should have 1 lift remaining");

        assertTrue(turnstile.tryPass(liftPass), "Second pass should be granted");
        assertEquals(0, liftPass.getRemainingLifts(), "Should have 0 lifts remaining");

        assertFalse(turnstile.tryPass(liftPass), "Third pass should be denied - no lifts left");
    }

    @Test
    void testBlockedPassDeniesAccess() {
        Strategy strategy = new OnSeasonStrategy();
        SkiPass pass = strategy.newSkiPass(LocalDateTime.now(), Lift.TEN, Time.SEASON);

        pass.block();

        boolean accessDenied = turnstile.tryPass(pass);
        assertFalse(accessDenied, "Blocked pass should deny access");
    }

    @Test
    void testWorkdayPassDeniesAccessOnWeekend() {
        Strategy strategy = new OnWorkdayStrategy();

        LocalDateTime workday = getNextWorkday();
        SkiPass workdayPass = strategy.newSkiPass(workday, Lift.TEN, Time.DAY1);

        LocalDateTime saturday = LocalDateTime.now()
                .with(TemporalAdjusters.nextOrSame(DayOfWeek.SATURDAY))
                .withHour(11)
                .withMinute(0);

        boolean denied = strategy.canAccess(workdayPass, saturday);
        assertFalse(denied, "Workday pass should deny access on weekend");
    }

    @Test
    void testTurnstileStatistics() {
        turnstile.clearLog();

        Strategy strategy = new OnSeasonStrategy();
        SkiPass validPass = strategy.newSkiPass(LocalDateTime.now(), Lift.INFINITE, Time.SEASON);
        SkiPass expiredPass = new SkiPass(
                "Season",
                LocalDateTime.now().minusDays(100),
                LocalDateTime.now().minusDays(1),
                Integer.MAX_VALUE,
                Time.SEASON,
                strategy
        );

        // 2 успішних проходи
        assertTrue(turnstile.tryPass(validPass));
        assertTrue(turnstile.tryPass(validPass));

        // 1 відмовлений прохід
        assertFalse(turnstile.tryPass(expiredPass));

        String generalStats = turnstile.getData();
        assertTrue(generalStats.contains("Total size: 3"), "Should have 3 total attempts");
        assertTrue(generalStats.contains("Granted: 2"), "Should have 2 granted");
        assertTrue(generalStats.contains("Denied: 1"), "Should have 1 denied");

        String typeStats = turnstile.getDataByType();
        assertTrue(typeStats.contains("Season"), "Should contain Season type");
        assertTrue(typeStats.contains("granted 2"), "Should show 2 granted for Season");
        assertTrue(typeStats.contains("denied 1"), "Should show 1 denied for Season");
    }

    private LocalDateTime getNextWorkday() {
        LocalDateTime date = LocalDateTime.now();
        while (date.getDayOfWeek() == DayOfWeek.SATURDAY ||
                date.getDayOfWeek() == DayOfWeek.SUNDAY) {
            date = date.plusDays(1);
        }
        return date;
    }
}