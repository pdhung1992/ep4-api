package jsb.ep4api.controllers;

import jsb.ep4api.entities.Language;
import jsb.ep4api.payloads.responses.LanguageResponse;
import jsb.ep4api.services.LanguageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/languages")
public class LanguageController {
    @Autowired
    private LanguageService languageService;

    @GetMapping
    public ResponseEntity<?> getAllLanguages() {
        try {
            List<Language> languages = languageService.getAllLanguages();
            List<LanguageResponse> responseList = languages.stream().map(
                    language -> {
                        LanguageResponse response = new LanguageResponse();
                        response.setId(language.getId());
                        response.setName(language.getName());
                        response.setCode(language.getCode());
                        response.setNativeName(language.getNativeName());
                        response.setSlug(language.getSlug());
                        return response;
                    }
            ).toList();
            return new ResponseEntity<>(responseList, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
