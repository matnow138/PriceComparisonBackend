package com.kodilla.price;

import com.kodilla.price.service.AmazonService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@AllArgsConstructor
@RestController
@RequestMapping("v1/amazon")
public class AmazonController {

    private final AmazonService amazonService;

    @GetMapping
    @ResponseBody
    public String getProduct(@RequestParam String id) throws Exception{
        return amazonService.getProduct(id);

    }
}
