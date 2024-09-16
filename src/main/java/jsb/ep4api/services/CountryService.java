package jsb.ep4api.services;

import jsb.ep4api.entities.Country;
import jsb.ep4api.repositories.CountryRepository;
import jsb.ep4api.specifications.CountrySpecifications;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CountryService {
    @Autowired
    private CountryRepository countryRepository;

    public List<Country> getAllCountries() {
        Specification<Country> spec = Specification.where(null);
        spec = spec.and(CountrySpecifications.hasNoDeleteFlag());
        return countryRepository.findAll(spec);
    }

    public Country getCountryById(Long id){
        Specification<Country> spec = Specification.where(null);
        spec = spec.and(CountrySpecifications.hasNoDeleteFlag());
        spec = spec.and(CountrySpecifications.hasId(id));
        return countryRepository.findOne(spec).orElse(null);
    }
}
