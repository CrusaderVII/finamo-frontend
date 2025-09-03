package com.egor.finamo.frontend.controller;

import com.egor.finamo.frontend.dto.CurrencyDto;
import com.egor.finamo.frontend.dto.CurrencyRateDto;
import com.egor.finamo.frontend.service.CurrencyRatesProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Collection;
import java.util.List;

@Controller
@RequestMapping("finamo/home")
@RequiredArgsConstructor
@Slf4j
public class MainPageController {

    private final CurrencyRatesProvider provider;

    @GetMapping()
    public String getHomePage(Model model,
                              ModelMap modelMap) {
        Collection<String> currencyCodes = provider.getMainCurrencies()
                .data()
                .getMainCurrencies()
                .stream()
                .map(CurrencyDto::code)
                .toList();

        Collection<CurrencyRateDto> rates = provider.getCurrencyRates(currencyCodes, "USD")
                .data()
                .getCurrencyRates();

        model.addAttribute("rates", rates);
        return "home";
    }
}
