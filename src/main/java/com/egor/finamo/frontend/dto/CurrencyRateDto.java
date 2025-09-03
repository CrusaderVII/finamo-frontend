package com.egor.finamo.frontend.dto;

import java.math.BigDecimal;

public record CurrencyRateDto(BigDecimal rate, CurrencyDto targetCurrency, CurrencyDto baseCurrency) {
}
