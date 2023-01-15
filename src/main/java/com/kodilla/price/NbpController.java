package com.kodilla.price;

import com.kodilla.price.domain.NbpDto;
import com.kodilla.price.service.CurrencyService;
import com.kodilla.price.service.NbpService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@AllArgsConstructor
@RestController
@RequestMapping("v1/nbp")
public class NbpController {
    private final NbpService nbpService;
    private final CurrencyService currencyService;

    @GetMapping(value ="/getExchangeRate/{currency}")
    public ResponseEntity<BigDecimal> getExchangeRate(@PathVariable String currency)throws Exception{
        return ResponseEntity.ok(nbpService.getCurrencyExchangeRate(currency));
    }

   @PostMapping
    public ResponseEntity<Void> addCurrency(String currency, String currencySymbol){
        currencyService.addCurrency(currency,currencySymbol);
        return ResponseEntity.ok().build();
    }

    @GetMapping(value = "/getCurrencyName/{currencySymbol}")
    public ResponseEntity<String> getCurrencyName(@PathVariable String currencySymbol){
        return ResponseEntity.ok(currencyService.getCurrencyName(currencySymbol));

    }

    @GetMapping(value = "/getCurrencySymbol/{currencyName}")
    public ResponseEntity<String> getCurrencySymbol(@PathVariable String currencyName){
        return ResponseEntity.ok(currencyService.getCurrencySymbol(currencyName));

    }
}
