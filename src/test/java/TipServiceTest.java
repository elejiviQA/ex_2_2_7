import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.ot5usk.Tester;
import org.ot5usk.TipService;

import java.math.BigDecimal;

import lombok.extern.java.Log;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.*;

@Log
public class TipServiceTest {

    private final TipService tipService = new TipService();

    public void log(Tester helper, String requirement) {
        AssertionError assertionError = null;
        StackTraceElement[] stackTraceElements = Thread.currentThread().getStackTrace();
        try {
            helper.test(requirement);
        } catch (AssertionError aErr) {
            assertionError = aErr;
        } finally {
            if (assertionError == null) {
                log.info("[TEST WAS SUCCESSFUL]: " + "\"" + requirement + "\"\n: " + stackTraceElements[2] + "\n");
            } else {
                log.warning("[TEST WAS FAILED]: " + "\"" + requirement + "\"\n: " + stackTraceElements[2] + "\n");
                throw assertionError;
            }
        }
    }

    public BigDecimal getExpectedAmount(BigDecimal amount, BigDecimal multiplier) {
        return amount.multiply(multiplier);
    }

    @DisplayName("Purchase amount value of a thousand rubles or more")
    @ParameterizedTest
    @CsvSource({
            "1000, 1.05",
            "2000, 1.05",
    })
    public void testRoundTips_WithAmountOfThousandOrMore_ResultMustIncreasedByFivePercent(BigDecimal amount, BigDecimal multiplier) {
        log((requirement) -> assertEquals(0, getExpectedAmount(amount, multiplier).compareTo(tipService.roundTips(amount))), "The purchase amount must be five percent more");
    }

    @DisplayName("The purchase amount value is less than a thousand rubles")
    @ParameterizedTest
    @CsvSource({
            "1, 1.1",
            "999, 1.1",
    })
    public void testRoundTips_WithAmountOfThousandOrMore_ResultMustIncreasedByTenPercent(BigDecimal amount, BigDecimal multiplier) {
        log((requirement) -> assertEquals(0, getExpectedAmount(amount, multiplier).compareTo(tipService.roundTips(amount))), "The purchase amount must be ten percent more");
    }

    @DisplayName("The purchase amount value is zero")
    @Test
    public void testRoundTips_WithZeroAmountValue_ResultMustBeZero() {
        log((requirement) -> assertEquals(0, BigDecimal.valueOf(0).compareTo(tipService.roundTips(BigDecimal.valueOf(0)))), "The purchase amount must be zero");
    }

    @DisplayName("The purchase amount value is null")
    @Test
    public void testRoundTips_WithEmptyAmountValue_ResultMustBeThrowException() {
        log((requirement) -> assertThrows(RuntimeException.class, () -> tipService.roundTips(null)), "Must throw exception");
    }

    @Disabled
    @DisplayName("The purchase amount value is null")
    @Test
    public void testRoundTips_WithEmptyAmountValue_ResultMustBeZero() {
        log((requirement) -> assertEquals(0, BigDecimal.valueOf(0).compareTo(tipService.roundTips(null))), "The purchase amount must be zero");
    }

    @Disabled
    @DisplayName("The purchase amount value is negative")
    @Test
    public void testRoundTips_WithNegativeAmountValue_ResultMustBeZero() {
        log((requirement) -> assertEquals(0, BigDecimal.valueOf(0).compareTo(tipService.roundTips(BigDecimal.valueOf(-1)))), "The purchase amount must be zero");
    }

    @Disabled
    @DisplayName("The purchase amount value is negative")
    @Test
    public void testRoundTips_WithNegativeAmountValue_ResultMustBeThrowException() {
        log((requirement) -> assertThrows(Exception.class, () -> tipService.roundTips(BigDecimal.valueOf(-1))), "Must throw exception");
    }
}