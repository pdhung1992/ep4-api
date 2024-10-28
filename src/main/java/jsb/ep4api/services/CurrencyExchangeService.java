package jsb.ep4api.services;

import jsb.ep4api.payloads.responses.ExchangeRateResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.math.BigDecimal;

import static jsb.ep4api.constants.Constants.*;

@Service
public class CurrencyExchangeService {
    private final RestTemplate restTemplate;
    @Autowired
    public CurrencyExchangeService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public BigDecimal convertUsdToVnd(BigDecimal amount) {
        String url = UriComponentsBuilder.fromHttpUrl(EXCHANGE_RATE_API_URL)
                .toUriString();

        ExchangeRateResponse response = restTemplate.getForObject(url, ExchangeRateResponse.class);

        if (response != null && response.getConversion_rates() != null && response.getConversion_rates().containsKey("VND")) {
            Double rate = response.getConversion_rates().get("VND");
            return amount.multiply(BigDecimal.valueOf(rate));
        } else {
            throw new RuntimeException("Unable to fetch exchange rate");
        }
    }
}
