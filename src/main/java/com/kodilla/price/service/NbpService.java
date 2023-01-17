package com.kodilla.price.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kodilla.price.domain.NbpDto;
import lombok.RequiredArgsConstructor;
import org.apache.http.client.utils.URIBuilder;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

@Service
@RequiredArgsConstructor
public class NbpService {

    private static HttpRequest createRequestForProduct(String currency) throws URISyntaxException {
        return HttpRequest.newBuilder()
                .uri(constructionUriForProduct(currency))
                .method("GET", HttpRequest.BodyPublishers.noBody())
                .build();

    }

    private static URI constructionUriForProduct(String currency) throws URISyntaxException {
        return new URIBuilder("http://api.nbp.pl/api/exchangerates/rates/c/" + currency + "/?format=json")
                .build();
    }

    public NbpDto getCurrency(String currency) throws Exception {
        HttpRequest request = createRequestForProduct(currency);
        HttpResponse<String> response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());

        String body = response.body();
        NbpDto nbpDto = new ObjectMapper().readValue(body, NbpDto.class);

        return nbpDto;

    }

    public BigDecimal getCurrencyExchangeRate(String currency) throws Exception {
        NbpDto nbpDto = getCurrency(currency);
        BigDecimal exchangeRate = nbpDto.getRates().get(0).getAsk();
        return exchangeRate;
    }


}
