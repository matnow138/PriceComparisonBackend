package com.kodilla.price;

import com.kodilla.price.domain.AmazonOfferDto;
import com.kodilla.price.domain.UserDto;
import com.kodilla.price.repository.AmazonDao;
import com.kodilla.price.service.AmazonService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("v1/amazon")
public class AmazonController {

    private final AmazonService amazonService;

    @PostMapping
    @ResponseBody
    public ResponseEntity<Void> addProduct(@RequestParam String id, long userID, BigDecimal targetPrice) throws Exception{
        amazonService.getProduct(id, userID, targetPrice);
        return ResponseEntity.ok().build();
    }

    @PatchMapping(value = "/deleteOffer/{id}")
    public ResponseEntity<Void> deleteOffer( @PathVariable long id){
        amazonService.deleteOffer(id);
        return ResponseEntity.ok().build();
    }

    @PatchMapping(value = "/updateOffer/")
    public ResponseEntity<AmazonOfferDto> updateOffer(@RequestBody AmazonOfferDto amazonOfferDto){
        return ResponseEntity.ok(amazonService.updateOffer(amazonOfferDto));
    }

    @GetMapping(value ="/getOffers/")
    public ResponseEntity<List<AmazonOfferDto>> getAllOffers(){
        return ResponseEntity.ok(amazonService.getAllOffers());
    }

    @PatchMapping(value = "/refreshPrices/")
    public ResponseEntity<Void> refreshPrices() throws Exception {
        amazonService.refreshPrices();
        return ResponseEntity.ok().build();
    }

    @PatchMapping(value ="/refreshPrice")
    public ResponseEntity<Void> refreshPrice(String asin) throws Exception{
        amazonService.refreshPrice(asin);
        return ResponseEntity.ok().build();
    }

    @GetMapping(value="/getUsers")
    public ResponseEntity<List<UserDto>> getUsersForOffer(long id){
        List<UserDto> userDtoList = amazonService.getOffersForUser(id);
        return ResponseEntity.ok(userDtoList);
    }

    @GetMapping(value="/getOffer/{id}")
    public ResponseEntity<AmazonOfferDto> getOffer(@PathVariable long id){
        return ResponseEntity.ok(amazonService.getOffer(id));
    }
}
