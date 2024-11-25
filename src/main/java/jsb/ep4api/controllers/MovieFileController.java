package jsb.ep4api.controllers;


import jsb.ep4api.entities.Movie;
import jsb.ep4api.payloads.responses.RequestResponse;
import jsb.ep4api.securities.service.UserDetailsImp;
import jsb.ep4api.services.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

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
    MovieService movieService;
    @Autowired
    private UserPackageService userPackageService;
    @Autowired
    PackageService packageService;
    @Autowired
    private MovieFileService movieFileService;

    private final Path moviePath = Paths.get("public/media");

    @GetMapping("/{filename:.+}")
    public ResponseEntity<?> getMovie(
            @PathVariable("filename") String filename,
            @RequestHeader(value = "Range", required = false) String range
    ) throws IOException {
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

            if ("Coming soon".equals(movie.getCategory().getName())) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new RequestResponse(
                        HttpStatus.FORBIDDEN.value(),
                        MOVIE_COMING_SOON_MESSAGE
                ));
            }

            Long movieId = movie.getId();
            Long packageId = movie.getAPackage().getId();

            // Check if user can watch movie
            boolean isFreeMovie = movieService.checkMovieIsFree(movieId);
            boolean isFreePackage = packageService.checkPackageIsFree(packageId);
            boolean canWatchMovie = userMovieService.checkUserCanWatchMovie(userId, movieId);
            boolean canWatchPackage = userPackageService.checkUserHasPackage(userId, packageId);
            boolean canWatchByAnotherPackage = userPackageService.checkUserCanWatchPackage(userId, packageId);

            if (isFreeMovie || isFreePackage || canWatchMovie || canWatchPackage || canWatchByAnotherPackage) {
                Path resolvedPath = moviePath.resolve(filename);
                Resource resource = new UrlResource(resolvedPath.toUri());

                if (!resource.exists() || !resource.isReadable()) {
                    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new RequestResponse(
                            HttpStatus.NOT_FOUND.value(),
                            MOVIE_FILE_NOT_FOUND_MESSAGE
                    ));
                }

                long resourceLength = resource.contentLength();

                if (range == null || range.isEmpty()) {
                    return ResponseEntity.ok()
                            .contentType(MediaType.parseMediaType("video/mp4"))
                            .contentLength(resourceLength)
                            .body(resource);
                } else {
                    String[] ranges = range.split("=")[1].split("-");
                    long start = Long.parseLong(ranges[0]);
                    long end = ranges.length > 1 ? Long.parseLong(ranges[1]) : resourceLength - 1;

                    if (start >= end || end >= resourceLength) {
                        return ResponseEntity.status(HttpStatus.REQUESTED_RANGE_NOT_SATISFIABLE).body(null);
                    }

                    long contentLength = end - start + 1;

                    byte[] data = new byte[(int) contentLength];

                    try (var inputStream = resource.getInputStream()) {
                        inputStream.skip(start);
                        inputStream.read(data, 0, (int) contentLength);
                    }

                    return ResponseEntity.status(HttpStatus.PARTIAL_CONTENT)
                            .contentType(MediaType.parseMediaType("video/mp4"))
                            .contentLength(contentLength)
                            .header("Content-Range", "bytes " + start + "-" + end + "/" + resourceLength)
                            .body(data);
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

