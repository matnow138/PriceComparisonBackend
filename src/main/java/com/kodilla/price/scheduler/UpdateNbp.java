package com.kodilla.price.scheduler;


import com.kodilla.price.service.NbpService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

@Configuration
@EnableScheduling
@RequiredArgsConstructor
public class UpdateNbp {

    private final NbpService nbpService;
    @Scheduled(fixedDelay = 1000000)
    public void updateCurrencies() throws Exception{
        nbpService.getCurrency("USD");

    }
}
