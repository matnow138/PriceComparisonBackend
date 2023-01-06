package com.kodilla.price.service;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectReader;
import com.kodilla.price.domain.NbpDto;
import com.kodilla.price.entity.Nbp;
import com.kodilla.price.mapper.NbpMapper;
import com.kodilla.price.repository.NbpDao;
import lombok.RequiredArgsConstructor;
import org.apache.http.client.utils.URIBuilder;
import org.springframework.stereotype.Service;

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

    private final NbpDao nbpDao;
    private final NbpMapper nbpMapper;
    private final ObjectMapper mapper = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);;


    public void getCurrency(String currency) throws Exception{
        HttpRequest request = createRequestForProduct(currency);
        HttpResponse<String> response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());

        String body = response.body();
        System.out.println(body);
        NbpDto result = new ObjectMapper().readValue(body, NbpDto.class);
        Nbp nbp = nbpMapper.mapToNbp(result);
        nbpDao.save(nbp);








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



}
