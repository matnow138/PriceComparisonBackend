package com.kodilla.price.service;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectReader;
import com.kodilla.price.domain.NbpDto;
import com.kodilla.price.entity.Nbp;
import com.kodilla.price.mapper.NbpMapper;
import com.kodilla.price.repository.NbpDao;
import com.kodilla.price.scheduler.CheckPrices;
import lombok.RequiredArgsConstructor;
import org.apache.http.client.utils.URIBuilder;
import org.hibernate.annotations.Check;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class NbpService {

    public NbpDto getCurrency(String currency) throws Exception{
        HttpRequest request = createRequestForProduct(currency);
        HttpResponse<String> response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());

        String body = response.body();
        NbpDto nbpDto = new ObjectMapper().readValue(body, NbpDto.class);

        return nbpDto;

    }

    private static HttpRequest createRequestForProduct(String currency)throws URISyntaxException{
        return HttpRequest.newBuilder()
                .uri(constructionUriForProduct(currency))
                .method("GET", HttpRequest.BodyPublishers.noBody())
                .build();

    }
    private static URI constructionUriForProduct(String currency) throws URISyntaxException {
        return new URIBuilder("http://api.nbp.pl/api/exchangerates/rates/c/"+ currency + "/?format=json")
                .build();
    }

    public BigDecimal getCurrencyExchangeRate(String currency)throws Exception{
        NbpDto nbpDto = getCurrency(currency);
        BigDecimal exchangeRate = nbpDto.getRates().get(0).getAsk();
        return exchangeRate;
    }




}
