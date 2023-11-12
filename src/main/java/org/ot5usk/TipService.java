package org.ot5usk;

import java.math.BigDecimal;

public class TipService {

    private final static BigDecimal BOUNDARY = BigDecimal.valueOf(1000);
    private final static BigDecimal TEN_PERCENT = BigDecimal.valueOf(1.1);
    private final static BigDecimal FIVE_PERCENT = BigDecimal.valueOf(1.05);

    public BigDecimal roundTips(BigDecimal amount) {
        if (amount.compareTo(BOUNDARY) < 0) {
            return amount.multiply(TEN_PERCENT);
        } else {
            return amount.multiply(FIVE_PERCENT);
        }
    }
}