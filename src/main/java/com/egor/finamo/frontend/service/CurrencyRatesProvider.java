package com.egor.finamo.frontend.service;

import com.egor.finamo.frontend.dto.response.CurrencyRateApiResponse;
import com.egor.finamo.frontend.dto.response.MainCurrenciesApiResponse;

import java.util.Collection;

public interface CurrencyRatesProvider {

    CurrencyRateApiResponse getCurrencyRates(Collection<String> targetCurrencyCodes, String baseCurrencyCode);
    MainCurrenciesApiResponse getMainCurrencies();
}
