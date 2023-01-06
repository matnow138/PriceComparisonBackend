package com.kodilla.price;

import com.kodilla.price.service.AmazonService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@AllArgsConstructor
@RestController
@RequestMapping("v1/amazon")
public class AmazonController {

    private final AmazonService amazonService;

    @GetMapping
    @ResponseBody
    public ResponseEntity<Void> addProduct(@RequestParam String id, long userID, double targetPrice) throws Exception{
        amazonService.getProduct(id, userID, targetPrice);
        return ResponseEntity.ok().build();
    }
}
