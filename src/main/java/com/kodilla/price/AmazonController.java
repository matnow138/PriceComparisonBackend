package com.kodilla.price;

import com.kodilla.price.domain.AmazonOfferDto;
import com.kodilla.price.domain.UserDto;
import com.kodilla.price.exception.OfferNotFoundException;
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
    public ResponseEntity<Void> addProduct(@RequestParam String id, @RequestParam String userId, @RequestParam BigDecimal targetPrice) throws Exception {
        amazonService.getProduct(id, userId, targetPrice);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping(value = "/deleteOffer/{id}")
    public ResponseEntity<Void> deleteOffer(@PathVariable long id) {
        amazonService.deleteOffer(id);
        return ResponseEntity.ok().build();
    }

    @PatchMapping(value = "/updateOffer")
    public ResponseEntity<AmazonOfferDto> updateOffer(@RequestBody AmazonOfferDto amazonOfferDto) throws OfferNotFoundException {
        return ResponseEntity.ok(amazonService.updateOffer(amazonOfferDto));
    }

    @GetMapping(value = "/getOffers")
    public ResponseEntity<List<AmazonOfferDto>> getAllOffers() {
        return ResponseEntity.ok(amazonService.getAllOffers());
    }

    @PatchMapping(value = "/refreshPrices")
    public ResponseEntity<Void> refreshPrices() throws Exception {
        amazonService.refreshPrices();
        return ResponseEntity.ok().build();
    }

    @PatchMapping(value = "/refreshPrice/{id}")
    public ResponseEntity<Void> refreshPrice(@PathVariable String id) throws Exception {
        amazonService.refreshPrice(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping(value = "/getUsers")
    public ResponseEntity<List<UserDto>> getUsersForOffer(long id) throws OfferNotFoundException {
        List<UserDto> userDtoList = amazonService.getOffersForUser(id);
        return ResponseEntity.ok(userDtoList);
    }

    @GetMapping(value = "/getOffer/{id}")
    public ResponseEntity<AmazonOfferDto> getOffer(@PathVariable long id) throws OfferNotFoundException {
        return ResponseEntity.ok(amazonService.getOffer(id));
    }
}
