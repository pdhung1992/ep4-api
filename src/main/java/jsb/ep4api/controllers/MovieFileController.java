package jsb.ep4api.controllers;


import jsb.ep4api.entities.Movie;
import jsb.ep4api.payloads.responses.RequestResponse;
import jsb.ep4api.securities.service.UserDetailsImp;
import jsb.ep4api.services.MovieFileService;
import jsb.ep4api.services.MovieService;
import jsb.ep4api.services.UserMovieService;
import jsb.ep4api.services.UserPackageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Path;
import java.nio.file.Paths;

import static jsb.ep4api.constants.Constants.*;

@RestController
@RequestMapping("/api/movie-files")
public class MovieFileController {
    @Autowired
    private UserMovieService userMovieService;
    @Autowired
    private UserPackageService userPackageService;
    @Autowired
    private MovieFileService movieFileService;

    private final Path moviePath = Paths.get("public/media");

    @GetMapping("/{filename:.+}")
    public ResponseEntity<?> getMovie(@PathVariable("filename") String filename) throws IOException {
        try {

            UserDetailsImp userDetails = (UserDetailsImp) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            Long userId = userDetails.getId();

            Movie movie = movieFileService.getMovieFileByFilename(filename).getMovie();
            if (movie == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new RequestResponse(
                        HttpStatus.NOT_FOUND.value(),
                        MOVIE_NOT_FOUND_MESSAGE
                ));
            }
            Long movieId = movie.getId();
            Long packageId = movie.getAPackage().getId();

            // Check if user can watch movie
            boolean canWatchMovie = userMovieService.checkUserCanWatchMovie(userId, movieId);
            boolean canWatchPackage = userPackageService.checkUserHasPackage(userId, packageId);

            if (canWatchMovie || canWatchPackage) {
                Path resolvedPath = moviePath.resolve(filename);
                Resource resource = new UrlResource(resolvedPath.toUri());
                if (resource.exists() && resource.isReadable()) {
                    return ResponseEntity.ok()
                            .contentType(MediaType.parseMediaType("video/mp4"))
                            .contentLength(resource.contentLength())
                            .body(resource);
                } else if(resource.exists() && !resource.isReadable()) {
                    return ResponseEntity.status(HttpStatus.FORBIDDEN).body(null);
                } else {
                    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new RequestResponse(
                            HttpStatus.NOT_FOUND.value(),
                            MOVIE_FILE_NOT_FOUND_MESSAGE
                    ));
                }
            }

            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new RequestResponse(
                    HttpStatus.FORBIDDEN.value(),
                    CAN_NOT_WATCH_MESSAGE
            ));

        } catch (MalformedURLException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }
}

