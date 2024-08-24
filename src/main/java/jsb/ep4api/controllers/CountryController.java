package jsb.ep4api.controllers;

import jsb.ep4api.entities.Country;
import jsb.ep4api.payloads.responses.CountryResponse;
import jsb.ep4api.services.CountryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/countries")
public class CountryController {
    @Autowired
    private CountryService countryService;

    @GetMapping
    public ResponseEntity<?> getAllCountries() {
        try {
            List<Country> countries = countryService.getAllCountries();
            List<CountryResponse> responseList = countries.stream().map(
                    country -> {
                        CountryResponse response = new CountryResponse();
                        response.setId(country.getId());
                        response.setName(country.getName());
                        response.setCode(country.getCode());
                        response.setSlug(country.getSlug());
                        return response;
                    }
            ).toList();
            return new ResponseEntity<>(responseList, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }
}
