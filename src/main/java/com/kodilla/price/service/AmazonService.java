package com.kodilla.price.service;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.kodilla.price.domain.AmazonDto;
import com.kodilla.price.mapper.AmazonMapper;
import com.kodilla.price.repository.AmazonDao;
//import com.mashape.unirest.http.HttpResponse;


import com.mashape.unirest.http.Unirest;
import lombok.RequiredArgsConstructor;
import net.bytebuddy.description.method.MethodDescription;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.asm.Type;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import com.fasterxml.jackson.core.*;
import com.fasterxml.jackson.databind.*;

import java.math.BigDecimal;
import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

@Service
@RequiredArgsConstructor
public class AmazonService {

    private final AmazonDao amazonDao;
    private final AmazonMapper amazonMapper;


    public void getProduct(String id) throws Exception{
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

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://amazon-product-price-data.p.rapidapi.com/product?asins=B005YQZ1KE&locale=US"))
                .header("X-RapidAPI-Key", "6922f7cae5mshe70274cbda6fed3p13cf1cjsnb65c3e41e70e")
                .header("X-RapidAPI-Host", "amazon-product-price-data.p.rapidapi.com")
                .method("GET", HttpRequest.BodyPublishers.noBody())
                .build();
        java.net.http.HttpResponse<String> response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
        System.out.println(response.body());

        ObjectMapper mapper = new ObjectMapper();

       JsonNode jsonNode = mapper.readTree(response.body());
       AmazonDto[] amazonDtos = mapper.convertValue(jsonNode, AmazonDto[].class);
       System.out.println(amazonDtos);






    }

}
