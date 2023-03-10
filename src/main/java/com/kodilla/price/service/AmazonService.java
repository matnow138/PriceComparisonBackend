package com.kodilla.price.service;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectReader;
import com.kodilla.price.domain.AmazonOfferDto;
import com.kodilla.price.domain.UserDto;
import com.kodilla.price.entity.AmazonOffer;
import com.kodilla.price.entity.User;
import com.kodilla.price.exception.OfferNotFoundException;
import com.kodilla.price.mapper.AmazonMapper;
import com.kodilla.price.mapper.UserMapper;
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
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AmazonService {

    private final AmazonDao amazonDao;
    private final AmazonMapper amazonMapper;
    private final UserDao userDao;
    private final UserMapper userMapper;
    private final ObjectMapper mapper = new ObjectMapper()
            .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    private final ObjectReader arrayReader = mapper.readerForArrayOf(AmazonOfferDto.class);

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

    public void getProduct(String id, String userID, BigDecimal targetPrice) throws Exception {

        HttpRequest request = createRequestForProduct(id);
        HttpResponse<String> response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());

        String body = response.body();
        AmazonOfferDto[] productsArrayNode = arrayReader.readValue(body, AmazonOfferDto[].class);

        AmazonOffer amazonOffer = amazonMapper.mapToAmazon(productsArrayNode[0]);
        try {

            User user = userDao.findById(Long.valueOf(userID)).orElse(null);
            user.getAmazonOfferList().add(amazonOffer);

        } catch (Exception e) {
            System.out.println("User not found");
        }
        amazonOffer.setTargetPrice(targetPrice);
        amazonDao.save(amazonOffer);

    }

    public AmazonOffer getOffer(String asin) throws Exception {

        HttpRequest request = createRequestForProduct(asin);
        HttpResponse<String> response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());

        String body = response.body();
        AmazonOfferDto[] productsArrayNode = arrayReader.readValue(body, AmazonOfferDto[].class);
        return amazonMapper.mapToAmazon(productsArrayNode[0]);


    }

    public URI generateUriForAlert(String asin) throws URISyntaxException {
        return new URIBuilder("www.amazon.com")
                .setPath("/dp/" + asin + "/")
                .build();
    }

    public void deleteOffer(long id) {
        amazonDao.deleteById(id);
    }

    public AmazonOfferDto updateOffer(AmazonOfferDto amazonOfferDto) throws OfferNotFoundException {
        if(amazonOfferDto.getId()!=null){
            AmazonOffer amazonOffer=amazonDao.findById(amazonOfferDto.getId()).orElseThrow(OfferNotFoundException::new);
            amazonOffer.setAsin(amazonOfferDto.getAsin());
            amazonOffer.setProductName(amazonOfferDto.getProduct_name());
            amazonOffer.setCurrentPrice(amazonOfferDto.getCurrentPrice());
            amazonOffer.setLocale(amazonOfferDto.getLocale());
            amazonOffer.setCurrencySymbol(amazonOfferDto.getCurrency_symbol());
            amazonOffer.setTargetPrice(amazonOfferDto.getTargetPrice());
            amazonDao.save(amazonOffer);
            return amazonMapper.mapToAmazonDto(amazonOffer);
        }else{
            return amazonOfferDto;
        }
    }

    public List<AmazonOfferDto> getAllOffers() {
        List<AmazonOffer> amazonOfferList = amazonDao.getAll();
        List<AmazonOfferDto> amazonOfferDtoList = new ArrayList<>();
        for (AmazonOffer amazonOffer : amazonOfferList) {
            amazonOfferDtoList.add(amazonMapper.mapToAmazonDto(amazonOffer));
        }
        return amazonOfferDtoList;
    }

    public void refreshPrices() throws Exception {
        List<AmazonOffer> amazonOfferList = amazonDao.getAll();

        for (AmazonOffer amazonOffer : amazonOfferList) {
            CreateRequestForProductUpdate(amazonOffer);
        }
    }

    private void CreateRequestForProductUpdate(AmazonOffer amazonOffer) throws URISyntaxException, java.io.IOException, InterruptedException {
        HttpRequest request = createRequestForProduct(amazonOffer.getAsin());
        HttpResponse<String> response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());

        String body = response.body();
        System.out.println(body);
        AmazonOfferDto[] productsArrayNode = arrayReader.readValue(body, AmazonOfferDto[].class);
        amazonOffer.setCurrentPrice(productsArrayNode[0].getCurrentPrice());
        amazonDao.save(amazonOffer);
    }

    public void refreshPrice(String id) throws OfferNotFoundException, Exception {
        AmazonOffer foundAmazon = amazonDao.findById(Long.valueOf(id)).orElseThrow(OfferNotFoundException::new);
        CreateRequestForProductUpdate(foundAmazon);
    }

    public List<UserDto> getOffersForUser(long id) throws OfferNotFoundException {
        AmazonOffer amazonOffer = amazonDao.findById(id).orElseThrow(OfferNotFoundException::new);
        List<User> userList = amazonOffer.getUserEntityList();
        List<UserDto> userDtoList = new ArrayList<>();
        for (User user : userList) {
            userDtoList.add(userMapper.mapToUserDto(user));
        }
        return userDtoList;
    }

    public AmazonOfferDto getOffer(long id) throws OfferNotFoundException {
        AmazonOffer amazonOffer = amazonDao.findById(id).orElseThrow(OfferNotFoundException::new);
        AmazonOfferDto amazonOfferDto = amazonMapper.mapToAmazonDto(amazonOffer);
        return amazonOfferDto;
    }


}
