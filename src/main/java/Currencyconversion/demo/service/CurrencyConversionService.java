package Currencyconversion.demo.service;

import Currencyconversion.demo.model.CurrencyRate;
import Currencyconversion.demo.repo.CurrencyRateRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class CurrencyConversionService {

    @Value("${currencybeacon.api.key}")
    private String apiKey;

    @Autowired
    private CurrencyRateRepository repository;

    private final RestTemplate restTemplate = new RestTemplate();


    public BigDecimal convert(String source, String target, Integer amount) {

        Optional<CurrencyRate> optionalRate =
                repository.findBySourceCurrencyAndTargetCurrency(source, target);

       
        if (optionalRate.isPresent()
                && optionalRate.get().getLastUpdated()
                .isAfter(LocalDateTime.now().minusHours(1))) {

            return optionalRate.get()
                    .getConversionRate()
                    .multiply(BigDecimal.valueOf(amount));
        }

        BigDecimal rate = callCurrencyBeacon(source, target,amount);

        CurrencyRate currencyRate = optionalRate.orElse(new CurrencyRate());
        currencyRate.setSourceCurrency(source);
        currencyRate.setTargetCurrency(target);
        currencyRate.setConversionRate(rate);
        currencyRate.setLastUpdated(LocalDateTime.now());

        repository.save(currencyRate);

        return rate.multiply(BigDecimal.valueOf(amount));
    }


    private BigDecimal callCurrencyBeacon(String source, String target,Integer amount) {

    	String url = "https://api.currencybeacon.com/v1/convert"
                + "?api_key=" + apiKey
                + "&from=" + source
                + "&to=" + target
                + "&amount=" + amount;

     
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(List.of(MediaType.APPLICATION_JSON));

        HttpEntity<Void> entity = new HttpEntity<>(headers);

        ResponseEntity<Map> response = restTemplate.exchange(
                url,
                HttpMethod.GET,
                entity,
                Map.class
        );

        if (response.getBody() == null) {
            throw new RuntimeException("Empty response from Currency Beacon");
        }

        Map body = response.getBody();

    

        Map responseObj = (Map) body.get("response");
        Double value = Double.valueOf(responseObj.get("value").toString());

        return BigDecimal.valueOf(value);
    }
}
