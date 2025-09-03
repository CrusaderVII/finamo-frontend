package com.egor.finamo.frontend.service;

import com.egor.finamo.frontend.dto.CurrencyRateDto;
import com.egor.finamo.frontend.dto.response.CurrencyRateApiResponse;
import com.egor.finamo.frontend.dto.response.MainCurrenciesApiResponse;
import com.egor.finamo.frontend.helper.GraphqlQueryLoader;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.io.IOException;
import java.util.Collection;

import static com.egor.finamo.frontend.helper.GraphqlQueryLoader.getCollectionVariable;

@Service
@RequiredArgsConstructor
@Slf4j
public class CurrencyRatesProviderStubImpl implements CurrencyRatesProvider {

    private final GraphqlQueryLoader queryLoader;
    private final WebClient webClient = WebClient.builder()
            .baseUrl("http://localhost:8080/graphql")
            .build();

    @Override
    public CurrencyRateApiResponse getCurrencyRates(Collection<String> targetCurrencyCodes, String baseCurrencyCode) {
        String query = String.format(queryLoader.getQuery("currencyRates.json"),
                getCollectionVariable(targetCurrencyCodes),
                baseCurrencyCode);
        log.info(query);
        try {
            Mono<String> response = webClient.post()
                    .header("Content-Type", "application/json")
                    .bodyValue(query)
                    .retrieve()
                    .bodyToMono(String.class);

            ObjectMapper mapper = new ObjectMapper();
            return mapper.readValue(response.block(),
                    CurrencyRateApiResponse.class);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public MainCurrenciesApiResponse getMainCurrencies() {
        String query = queryLoader.getQuery("mainCurrencies.json");
        try {
            Mono<String> response = webClient.post()
                    .header("Content-Type", "application/json")
                    .bodyValue(query)
                    .retrieve()
                    .bodyToMono(String.class);

            ObjectMapper mapper = new ObjectMapper();
            MainCurrenciesApiResponse mainCurrenciesApiResponse = mapper
                    .readValue(response.block(), MainCurrenciesApiResponse.class);

            log.debug(mainCurrenciesApiResponse.data().getMainCurrencies()+"");
            return mainCurrenciesApiResponse;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
