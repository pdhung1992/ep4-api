package jsb.ep4api.services;

import jsb.ep4api.entities.Language;
import jsb.ep4api.repositories.LanguageRepository;
import jsb.ep4api.specifications.LanguageSpecifications;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LanguageService {
    @Autowired
    private LanguageRepository languageRepository;

    public List<Language> getAllLanguages() {
        Specification<Language> spec = Specification.where(null);
        spec = spec.and(LanguageSpecifications.hasNoDeleteFlag());
        return languageRepository.findAll(spec);
    }
}
