package com.kodilla.price.service;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectReader;
import com.kodilla.price.domain.AmazonDto;
import com.kodilla.price.mapper.AmazonMapper;
import com.kodilla.price.repository.AmazonDao;
import lombok.RequiredArgsConstructor;
import org.apache.http.client.utils.URIBuilder;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Iterator;

@Service
@RequiredArgsConstructor
public class AmazonService {

    private final AmazonDao amazonDao;
    private final AmazonMapper amazonMapper;
    private final ObjectMapper mapper = new ObjectMapper()
            .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
      private final ObjectReader arrayReader = mapper.readerForArrayOf(AmazonDto.class);


    public String getProduct(String id) throws Exception {
      /*  String host = "https://amazon-product-price-data.p.rapidapi.com/product";
        String charset = "UTF-8";

        String x_rapidapi_host = "amazon-product-price-data.p.rapidapi.com";
        String x_rapidapi_key = "6922f7cae5mshe70274cbda6fed3p13cf1cjsnb65c3e41e70e";

       String asins = "asins=";
       String locale = "&locale=US";
       HttpResponse<String> response = Unirest.get(host + "?" + asins +id + locale)
               .header("X-RapidAPI-Key",x_rapidapi_key)
               .header("X-RapidAPI-Host",x_rapidapi_host)
               .asString();
*/

        HttpRequest request = createRequestForProduct(id);
        HttpResponse<String> response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());

        String body = response.body();
        System.out.println(body);
        AmazonDto[] productsArrayNode = arrayReader.readValue(body, AmazonDto[].class);
        return productsArrayNode[0].toString();
    }

    private static HttpRequest createRequestForProduct(String id) throws URISyntaxException {
        return HttpRequest.newBuilder()
                .uri(constructUriForProduct(id))
                .header("X-RapidAPI-Key", "6922f7cae5mshe70274cbda6fed3p13cf1cjsnb65c3e41e70e")
                .header("X-RapidAPI-Host", "amazon-product-price-data.p.rapidapi.com")
                .method("GET", HttpRequest.BodyPublishers.noBody())
                .build();
    }

    private static URI constructUriForProduct(String id) throws URISyntaxException {
        return new URIBuilder("https://amazon-product-price-data.p.rapidapi.com")
                .setPath("/product")
                .addParameter("asins", id)
                .addParameter("locale", "US")
                .build();
    }

}
