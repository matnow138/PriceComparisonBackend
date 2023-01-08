package com.kodilla.price.service;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectReader;
import com.kodilla.price.domain.AmazonDto;
import com.kodilla.price.entity.Amazon;
import com.kodilla.price.entity.User;
import com.kodilla.price.mapper.AmazonMapper;
import com.kodilla.price.repository.AmazonDao;
import com.kodilla.price.repository.UserDao;
import lombok.RequiredArgsConstructor;
import org.apache.http.client.utils.URIBuilder;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.math.BigDecimal;
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
    private final UserDao userDao;
    private final ObjectMapper mapper = new ObjectMapper()
            .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
      private final ObjectReader arrayReader = mapper.readerForArrayOf(AmazonDto.class);


    public void getProduct(String id, long userID, BigDecimal targetPrice) throws Exception {

        HttpRequest request = createRequestForProduct(id);
        HttpResponse<String> response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());

        String body = response.body();
        System.out.println(body);
        AmazonDto[] productsArrayNode = arrayReader.readValue(body, AmazonDto[].class);

        Amazon amazon = amazonMapper.mapToAmazon(productsArrayNode[0]);
        try {

            User user = userDao.findById(userID).orElse(null);
            user.getAmazonList().add(amazon);

        }catch (Exception e){
            System.out.println("User not found");
        }
        amazon.setTargetPrice(targetPrice);
        amazonDao.save(amazon);

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
