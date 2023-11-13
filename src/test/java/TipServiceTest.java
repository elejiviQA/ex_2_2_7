import jdk.jfr.Description;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;
import org.ot5usk.TipService;

import java.math.BigDecimal;
import java.util.stream.Stream;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Test roundTips method")
public class TipServiceTest {

    private final TipService tipService = new TipService();

    public BigDecimal getExpectedAmount(BigDecimal amount, BigDecimal multiplier) {
        return amount.multiply(multiplier);
    }

    @DisplayName("Amount from positive args")
    @Description("If the amount is <1000, then 10% is added to it, otherwise 5%")
    @ParameterizedTest(name = "{2} tip is added to the amount of {0} rubles")
    @CsvFileSource(resources = "/testData.csv")
    public void withPositiveSource(BigDecimal amount, BigDecimal multiplier, String percent) {
        assertEquals(getExpectedAmount(amount, multiplier), tipService.roundTips(amount), "The purchase amount must been " + percent + " more");
    }

    @DisplayName("Amount from zero and less zero")
    @Description("Test calculate")
    @ParameterizedTest(name = "If {0}, then {1}")
    @CsvSource({"0, 0", "-1, -1.1"})
    public void withObjectionableSource(BigDecimal amount, BigDecimal result) {
        assertEquals(result.intValue(), tipService.roundTips(amount).intValue(), "Must been " + result);
    }

    @Disabled
    @DisplayName("Amount from negative args")
    @Description("Test catching ex")
    @ParameterizedTest(name = "If purchase amount value is {0}")
    @MethodSource("negativeArgs")
    public void withNegativeSourceMustBeenEx(BigDecimal negativeAmountValue) {
        assertThrows(RuntimeException.class, () -> tipService.roundTips(negativeAmountValue), "Must throw ex");
    }

    @Disabled
    @DisplayName("Amount from negative args")
    @Description("Test nullable")
    @ParameterizedTest(name = "If purchase amount value is {0}")
    @MethodSource("negativeArgs")
    public void withNullableSourceMustBeenZero(BigDecimal negativeAmountValue) {
        assertEquals(0, tipService.roundTips(negativeAmountValue).intValue(), "Must been 0");
    }

    static Stream<BigDecimal> negativeArgs() {
        return Stream.of(null, BigDecimal.valueOf(-1));
    }
}