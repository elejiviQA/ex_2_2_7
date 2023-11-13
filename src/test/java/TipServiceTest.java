import lombok.extern.java.Log;
import org.junit.jupiter.api.DisplayName;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.ot5usk.Tester;
import org.ot5usk.TipService;

import java.math.BigDecimal;

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

    @DisplayName("Purchase amount of a thousand rubles or more")
    @ParameterizedTest
    @CsvSource({
            "1000, 1.05",
            "2000, 1.05",
    })
    public void testRoundTips_WithAmountOfThousandOrMore_AmountShouldIncreasedByFivePercent(BigDecimal amount, BigDecimal multiplier) {
        log((requirement) -> assertEquals(getExpectedAmount(amount, multiplier), tipService.roundTips(amount)), "The purchase amount must be five percent more");
    }

    @DisplayName("The purchase amount is less than a thousand rubles")
    @ParameterizedTest
    @CsvSource({
            "1, 1.1",
            "999, 1.1",
    })
    public void testRoundTips_WithAmountOfThousandOrMore_AmountShouldIncreasedByTenPercent(BigDecimal amount, BigDecimal multiplier) {
        log((requirement) -> assertEquals(getExpectedAmount(amount, multiplier), tipService.roundTips(amount)), "The purchase amount must be ten percent more");
    }
}