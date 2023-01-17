package com.kodilla.price.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.gson.annotations.SerializedName;
import com.kodilla.price.entity.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Data
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class AmazonOfferDto {

    private long id;
    @JsonProperty("asin")
    private String asin;
    @JsonProperty("product_name")
    private String product_name;
    @JsonProperty("current_price")
    private BigDecimal currentPrice;
    @JsonProperty("locale")
    private String locale;
    @SerializedName("currency_symbol")
    private String currency_symbol;
    private BigDecimal targetPrice;
    private List<User> userEntityList;


}
