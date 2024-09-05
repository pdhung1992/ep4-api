package jsb.ep4api.controllers;


import jsb.ep4api.entities.Genre;
import jsb.ep4api.payloads.responses.GenreResponse;
import jsb.ep4api.services.GenreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static jsb.ep4api.constants.Constants.INTERNAL_SERVER_ERROR_MESSAGE;

@RestController
@RequestMapping("/api/genres")
public class GenreController {
    @Autowired
    private GenreService genreService;

    @GetMapping
    public ResponseEntity<?> getAllGenres() {
        try {
            List<Genre> genres = genreService.getAllGenres();
            List<GenreResponse> responses = genres.stream().map(
                    genre -> {
                        GenreResponse response = new GenreResponse();
                        response.setId(genre.getId());
                        response.setName(genre.getName());
                        response.setDescription(genre.getDescription());
                        response.setSlug(genre.getSlug());
                        return response;
                    }
            ).toList();
            return new ResponseEntity<>(responses, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(INTERNAL_SERVER_ERROR_MESSAGE, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
