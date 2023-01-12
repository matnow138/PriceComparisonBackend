package com.kodilla.price.service;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectReader;
import com.kodilla.price.domain.AmazonOfferDto;
import com.kodilla.price.entity.AmazonOffer;
import com.kodilla.price.entity.User;
import com.kodilla.price.mapper.AmazonMapper;
import com.kodilla.price.repository.AmazonDao;
import com.kodilla.price.repository.UserDao;
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
public class AmazonService {

    private final AmazonDao amazonDao;
    private final AmazonMapper amazonMapper;
    private final UserDao userDao;
    private final ObjectMapper mapper = new ObjectMapper()
            .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
      private final ObjectReader arrayReader = mapper.readerForArrayOf(AmazonOfferDto.class);


    public void getProduct(String id, long userID, BigDecimal targetPrice) throws Exception {

        HttpRequest request = createRequestForProduct(id);
        HttpResponse<String> response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());

        String body = response.body();
        System.out.println(body);
        AmazonOfferDto[] productsArrayNode = arrayReader.readValue(body, AmazonOfferDto[].class);

        AmazonOffer amazonOffer = amazonMapper.mapToAmazon(productsArrayNode[0]);
        try {

            User user = userDao.findById(userID).orElse(null);
            user.getAmazonOfferList().add(amazonOffer);

        }catch (Exception e){
            System.out.println("User not found");
        }
        amazonOffer.setTargetPrice(targetPrice);
        amazonDao.save(amazonOffer);

    }

    public AmazonOffer getOffer(String asin) throws Exception {

            HttpRequest request = createRequestForProduct(asin);
            HttpResponse<String> response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());

            String body = response.body();
            //System.out.println(body);
            AmazonOfferDto[] productsArrayNode = arrayReader.readValue(body, AmazonOfferDto[].class);
            return amazonMapper.mapToAmazon(productsArrayNode[0]);




    }

    public static HttpRequest createRequestForProduct(String id) throws URISyntaxException {
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
