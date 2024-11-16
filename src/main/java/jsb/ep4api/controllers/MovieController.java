package jsb.ep4api.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.validation.Valid;
import jsb.ep4api.config.HasFunctionAccessToClass;
import jsb.ep4api.config.HasFunctionAccessToFunction;
import jsb.ep4api.entities.*;
import jsb.ep4api.entities.Package;
import jsb.ep4api.payloads.requests.CastRequest;
import jsb.ep4api.payloads.requests.CrewMemberRequest;
import jsb.ep4api.payloads.requests.MovieFileRequest;
import jsb.ep4api.payloads.responses.*;
import jsb.ep4api.securities.service.AdminDetailsImp;
import jsb.ep4api.securities.service.UserDetailsImp;
import jsb.ep4api.services.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static jsb.ep4api.constants.Constants.*;

@RestController
@RequestMapping("/api/movies")
public class MovieController {
    @Autowired
    private MovieService movieService;
    @Autowired
    UserMovieService userMovieService;
    @Autowired
    PackageService packageService;
    @Autowired
    UserPackageService userPackageService;
    @Autowired
    CountryService countryService;
    @Autowired
    StudioService studioService;
    @Autowired
    VideoModeService videoModeService;
    @Autowired
    ClassificationService classificationService;
    @Autowired
    CategoryService categoryService;
    @Autowired
    AdminService adminService;
    @Autowired
    GenreService genreService;
    @Autowired
    MovieGenreService movieGenreService;
    @Autowired
    LanguageService languageService;
    @Autowired
    MovieLanguageService movieLanguageService;
    @Autowired
    MovieFileService movieFileService;
    @Autowired
    CastService castService;
    @Autowired
    CrewMemberService crewMemberService;
    @Autowired
    CrewPositionService crewPositionService;
    @Autowired
    RatingService ratingService;
    @Autowired
    ReviewService reviewService;

    @GetMapping("/admin")
    @HasFunctionAccessToFunction(MOVIE_MANAGEMENT_FUNCTION)
    public ResponseEntity<?> getAllMovies(
            @RequestParam(required = false) Integer pageNo,
            @RequestParam(required = false) Integer pageSize,
            @RequestParam(required = false) String sortField,
            @RequestParam(required = false) String sortDir,
            @RequestParam(required = false) String title,
            @RequestParam(required = false) Long countryId
    ) throws JsonProcessingException {
        try {
            if (pageNo == null) {
                pageNo = 1;
            }
            if (pageSize == null) {
                pageSize = 10;
            }
            if (sortField == null) {
                sortField = "id";
            }
            if (sortDir == null) {
                sortDir = "asc";
            }

            Page<Movie> movies = movieService.getMovies(pageNo, pageSize, sortField, sortDir, title, countryId);
            List<Movie> movieList = movies.getContent();
            List<MovieResponse> movieResponses = new ArrayList<>();

            for (Movie movie : movieList) {
                MovieResponse movieResponse = new MovieResponse();
                movieResponse.setId(movie.getId());
                movieResponse.setTitle(movie.getTitle());
                movieResponse.setOriginalTitle(movie.getOriginalTitle());
                movieResponse.setDuration(movie.getDuration());
                movieResponse.setReleaseYear(movie.getReleaseYear());
                movieResponse.setShow(movie.isShow());
                movieResponse.setShowAtHome(movie.isShowAtHome());
                movieResponse.setPoster(movie.getPoster());
                movieResponse.setSlug(movie.getSlug());

                movieResponses.add(movieResponse);
            }

            int totalPages = movies.getTotalPages();
            long totalItems = movies.getTotalElements();
            long fromItem = (pageNo - 1) * pageSize + 1;
            long toItem = 0;
            if (totalItems > pageSize) {
                toItem = fromItem + pageSize - 1;
            } else {
                toItem = totalItems;
            }

            SpecResponse specResponse = new SpecResponse();
            specResponse.setCurrentPage(pageNo);
            specResponse.setTotalPages(totalPages);
            specResponse.setTotalItems(totalItems);
            specResponse.setFromItem(fromItem);
            specResponse.setToItem(toItem);
            specResponse.setPageSize(pageSize);
            specResponse.setSortField(sortField);
            specResponse.setSortDir(sortDir);
            specResponse.setData(movieResponses);

            return ResponseEntity.ok(specResponse);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("admin/details/{id}")
    @HasFunctionAccessToFunction(MOVIE_MANAGEMENT_FUNCTION)
    public ResponseEntity<?> getMovieDetailsForAdmin(@PathVariable Long id){
        try {
            Movie movie = movieService.getMovieById(id);
            if (movie == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(MOVIE_NOT_FOUND_MESSAGE);
            }

            MovieResponse movieResponse = new MovieResponse();
            movieResponse.setId(movie.getId());
            movieResponse.setTitle(movie.getTitle());
            movieResponse.setOriginalTitle(movie.getOriginalTitle());
            movieResponse.setCanRent(movie.isCanRent());
            movieResponse.setPrice(movie.getPrice());
            movieResponse.setPackageName(movie.getAPackage().getPackageName());
            movieResponse.setViews(movie.getViews());
            movieResponse.setStoryLine(movie.getStoryline());
            movieResponse.setPoster(movie.getPoster());
            movieResponse.setImage(movie.getImage());
            movieResponse.setTrailer(movie.getTrailer());
            movieResponse.setDuration(movie.getDuration());
            movieResponse.setReleaseYear(movie.getReleaseYear());
            movieResponse.setCountry(movie.getCountry().getName());
            movieResponse.setStudio(movie.getStudio().getName());
            movieResponse.setVideoMode(movie.getVideoMode().getName());
            movieResponse.setClassification("[" + movie.getClassification().getCode() + "] " + movie.getClassification().getName());
            movieResponse.setCategory(movie.getCategory().getName());
            movieResponse.setShowAtHome(movie.isShowAtHome());
            movieResponse.setShow(movie.isShow());

            List<MovieGenre> movieGenres = movieGenreService.getMovieGenresByMovieId(id);
            List<GenreResponse> genres = new ArrayList<>();
            for (MovieGenre movieGenre : movieGenres) {
                GenreResponse genreResponse = new GenreResponse();
                genreResponse.setId(movieGenre.getGenre().getId());
                genreResponse.setName(movieGenre.getGenre().getName());
                genreResponse.setSlug(movieGenre.getGenre().getSlug());
                genres.add(genreResponse);
            }
            movieResponse.setGenres(genres);

            List<MovieLanguage> movieLanguages = movieLanguageService.getMovieLanguagesByMovieId(id);
            List<String> languageResponses = new ArrayList<>();
            for (MovieLanguage movieLanguage : movieLanguages) {
                languageResponses.add(movieLanguage.getLanguage().getNativeName());
            }
            movieResponse.setLanguages(languageResponses);
            return ResponseEntity.ok(movieResponse);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/client/show")
    public ResponseEntity<?> getShowMovies(
            @RequestParam(required = false) Integer pageNo,
            @RequestParam(required = false) Integer pageSize,
            @RequestParam(required = false) String sortField,
            @RequestParam(required = false) String sortDir,
            @RequestParam(required = false) String title,
            @RequestParam(required = false) Long countryId
    ) throws JsonProcessingException{
        try {
            if (pageNo == null) {
                pageNo = 1;
            }
            if (pageSize == null) {
                pageSize = 10;
            }
            if (sortField == null) {
                sortField = "id";
            }
            if (sortDir == null) {
                sortDir = "asc";
            }

            Page<Movie> movies = movieService.getDisplayMovies(pageNo, pageSize, sortField, sortDir, title, countryId);
            List<Movie> movieList = movies.getContent();
            List<MovieResponse> movieResponses = new ArrayList<>();

            for (Movie movie : movieList) {
                MovieResponse movieResponse = new MovieResponse();
                movieResponse.setId(movie.getId());
                movieResponse.setSlug(movie.getSlug());
                movieResponse.setTitle(movie.getTitle());
                movieResponse.setOriginalTitle(movie.getOriginalTitle());
                movieResponse.setPrice(movie.getPrice());
                movieResponse.setDuration(movie.getDuration());
                movieResponse.setReleaseYear(movie.getReleaseYear());
                movieResponse.setPoster(movie.getPoster());
                movieResponse.setPackageId(movie.getAPackage().getId());
                movieResponse.setCountryId(movie.getCountry().getId());
                movieResponse.setStudioId(movie.getStudio().getId());
                movieResponse.setVideoModeId(movie.getVideoMode().getId());
                movieResponse.setClassificationId(movie.getClassification().getId());
                movieResponse.setCategoryId(movie.getCategory().getId());

                movieResponses.add(movieResponse);
            }

            int totalPages = movies.getTotalPages();
            long totalItems = movies.getTotalElements();
            long fromItem = (pageNo - 1) * pageSize + 1;
            long toItem = 0;
            if (totalItems > pageSize) {
                toItem = fromItem + pageSize - 1;
            } else {
                toItem = totalItems;
            }

            SpecResponse specResponse = new SpecResponse();
            specResponse.setCurrentPage(pageNo);
            specResponse.setTotalPages(totalPages);
            specResponse.setTotalItems(totalItems);
            specResponse.setFromItem(fromItem);
            specResponse.setToItem(toItem);
            specResponse.setPageSize(pageSize);
            specResponse.setSortField(sortField);
            specResponse.setSortDir(sortDir);
            specResponse.setData(movieResponses);

            return ResponseEntity.ok(specResponse);
        }catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/client/showAtHome")
    public ResponseEntity<?> getShowAtHomeMovies() throws JsonProcessingException {
        try {
            List<Movie> movies = movieService.getShowAtHomeMovies();
            List<MovieResponse> movieResponses = new ArrayList<>();

            for (Movie movie : movies) {
                MovieResponse movieResponse = new MovieResponse();
                movieResponse.setId(movie.getId());
                movieResponse.setSlug(movie.getSlug());
                movieResponse.setTitle(movie.getTitle());
                movieResponse.setOriginalTitle(movie.getOriginalTitle());
                movieResponse.setDuration(movie.getDuration());
                movieResponse.setReleaseYear(movie.getReleaseYear());
                movieResponse.setPoster(movie.getPoster());
                movieResponse.setImage(movie.getImage());
                movieResponse.setClassification(movie.getClassification().getCode());
                movieResponse.setVideoMode(movie.getVideoMode().getCode());
                List<MovieGenre> movieGenres = movieGenreService.getMovieGenresByMovieId(movie.getId());
                List<GenreResponse> genres = new ArrayList<>();
                for (MovieGenre movieGenre : movieGenres) {
                    GenreResponse genreResponse = new GenreResponse();
                    genreResponse.setId(movieGenre.getGenre().getId());
                    genreResponse.setName(movieGenre.getGenre().getName());
                    genreResponse.setSlug(movieGenre.getGenre().getSlug());
                    genres.add(genreResponse);
                }
                movieResponse.setGenres(genres);

                movieResponses.add(movieResponse);
            }

            return ResponseEntity.ok(movieResponses);
        }catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/client/home/category/{categoryId}")
    public ResponseEntity<?> get10MoviesByCategoryId(
            @PathVariable Long categoryId
    ) {
        try {
            Category category = categoryService.getCategoryById(categoryId);
            if (category == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(CATEGORY_NOT_FOUND_MESSAGE);
            }
            List<Movie> movies = movieService.get10MoviesByCategoryId(category.getId());
            List<MovieResponse> movieResponses = new ArrayList<>();

            for (Movie movie : movies) {
                MovieResponse movieResponse = new MovieResponse();
                movieResponse.setId(movie.getId());
                movieResponse.setSlug(movie.getSlug());
                movieResponse.setTitle(movie.getTitle());
                movieResponse.setOriginalTitle(movie.getOriginalTitle());
                movieResponse.setDuration(movie.getDuration());
                movieResponse.setReleaseYear(movie.getReleaseYear());
                movieResponse.setPoster(movie.getPoster());
                movieResponse.setImage(movie.getImage());
                movieResponse.setClassification(movie.getClassification().getCode());
                movieResponse.setVideoMode(movie.getVideoMode().getCode());
                List<MovieGenre> movieGenres = movieGenreService.getMovieGenresByMovieId(movie.getId());
                List<GenreResponse> genres = new ArrayList<>();
                for (MovieGenre movieGenre : movieGenres) {
                    GenreResponse genreResponse = new GenreResponse();
                    genreResponse.setId(movieGenre.getGenre().getId());
                    genreResponse.setName(movieGenre.getGenre().getName());
                    genreResponse.setSlug(movieGenre.getGenre().getSlug());
                    genres.add(genreResponse);
                }
                movieResponse.setGenres(genres);
                Double rating = ratingService.getAverageRatingByMovieId(movie.getId());
                Long ratingCount = ratingService.getRatingCountByMovieId(movie.getId());
                movieResponse.setRating(rating);
                movieResponse.setRatingCount(ratingCount);

                movieResponses.add(movieResponse);
            }

            return ResponseEntity.ok(movieResponses);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/client/genre/best/{slug}")
    public ResponseEntity<?> get10BestMoviesByGenres(@PathVariable String slug) {
        try {
            Genre genre = genreService.getGenreBySlug(slug);
            if (genre == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(GENRE_NOT_FOUND_MESSAGE);
            }
            List<Long> movieIds = movieGenreService.getMovieIdsByGenreId(genre.getId());
            List<Movie> movies = movieService.get10MoviesBestByGenreId(movieIds);
            System.out.printf("Movies: %s\n", movies);
            List<MovieResponse> movieResponses = new ArrayList<>();
            for (Movie movie : movies) {
                MovieResponse movieResponse = new MovieResponse();
                movieResponse.setId(movie.getId());
                movieResponse.setSlug(movie.getSlug());
                movieResponse.setTitle(movie.getTitle());
                movieResponse.setOriginalTitle(movie.getOriginalTitle());
                movieResponse.setDuration(movie.getDuration());
                movieResponse.setReleaseYear(movie.getReleaseYear());
                movieResponse.setPoster(movie.getPoster());
                movieResponse.setImage(movie.getImage());
                movieResponse.setClassification(movie.getClassification().getCode());
                movieResponse.setVideoMode(movie.getVideoMode().getCode());
                List<MovieGenre> movieGenres = movieGenreService.getMovieGenresByMovieId(movie.getId());
                List<GenreResponse> genres = new ArrayList<>();
                for (MovieGenre movieGenre : movieGenres) {
                    GenreResponse genreResponse = new GenreResponse();
                    genreResponse.setId(movieGenre.getGenre().getId());
                    genreResponse.setName(movieGenre.getGenre().getName());
                    genreResponse.setSlug(movieGenre.getGenre().getSlug());
                    genres.add(genreResponse);
                }
                movieResponse.setGenres(genres);
                Double rating = ratingService.getAverageRatingByMovieId(movie.getId());
                Long ratingCount = ratingService.getRatingCountByMovieId(movie.getId());
                movieResponse.setRating(rating);
                movieResponse.setRatingCount(ratingCount);
                movieResponse.setViews(movie.getViews());

                movieResponses.add(movieResponse);
            }

            return ResponseEntity.ok(movieResponses);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/client/details/related/{movieId}")
    public ResponseEntity<?> getRelatedMovieAtDetailsPage(
            @PathVariable Long movieId
    ) {
        try {
            Movie movie = movieService.getMovieById(movieId);
            if (movie == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(MOVIE_NOT_FOUND_MESSAGE);
            }
            List<Movie> movies = movieService.get4MoviesByCategoryId(movie.getCategory().getId(), movie.getId());
            List<MovieResponse> movieResponses = new ArrayList<>();

            for (Movie relatedMovie : movies) {
                MovieResponse movieResponse = new MovieResponse();
                movieResponse.setId(relatedMovie.getId());
                movieResponse.setSlug(relatedMovie.getSlug());
                movieResponse.setTitle(relatedMovie.getTitle());
                movieResponse.setOriginalTitle(relatedMovie.getOriginalTitle());
                movieResponse.setDuration(relatedMovie.getDuration());
                movieResponse.setReleaseYear(relatedMovie.getReleaseYear());
                movieResponse.setPoster(relatedMovie.getPoster());
                movieResponse.setImage(relatedMovie.getImage());
                movieResponse.setClassification(relatedMovie.getClassification().getCode());
                movieResponse.setVideoMode(relatedMovie.getVideoMode().getCode());
                List<MovieGenre> movieGenres = movieGenreService.getMovieGenresByMovieId(relatedMovie.getId());
                List<GenreResponse> genres = new ArrayList<>();
                for (MovieGenre movieGenre : movieGenres) {
                    GenreResponse genreResponse = new GenreResponse();
                    genreResponse.setId(movieGenre.getGenre().getId());
                    genreResponse.setName(movieGenre.getGenre().getName());
                    genreResponse.setSlug(movieGenre.getGenre().getSlug());
                    genres.add(genreResponse);
                }
                movieResponse.setGenres(genres);
                Double rating = ratingService.getAverageRatingByMovieId(movie.getId());
                Long ratingCount = ratingService.getRatingCountByMovieId(movie.getId());
                movieResponse.setRating(rating);
                movieResponse.setRatingCount(ratingCount);

                movieResponses.add(movieResponse);
            }

            return ResponseEntity.ok(movieResponses);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/client/details/{slug}/{userId}")
    public ResponseEntity<?> getMovieDetailsForClient(@PathVariable String slug,
                                                      @PathVariable Long userId
    ){
        try {
            Movie movie = movieService.getMovieBySlug(slug);
            if (movie == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(MOVIE_NOT_FOUND_MESSAGE);
            }
            Double rating = ratingService.getAverageRatingByMovieId(movie.getId());
            Long ratingCount = ratingService.getRatingCountByMovieId(movie.getId());
            int userRating = 0;
            if (userId != null){
                userRating = ratingService.getRatingValueByUserIdAndMovieId(userId, movie.getId());
            }
            Long reviewCount = reviewService.getReviewCountByMovieId(movie.getId());

            MovieResponse movieResponse = new MovieResponse();
            movieResponse.setId(movie.getId());
            movieResponse.setTitle(movie.getTitle());
            movieResponse.setOriginalTitle(movie.getOriginalTitle());
            movieResponse.setCanRent(movie.isCanRent());
            movieResponse.setPrice(movie.getPrice());
            movieResponse.setPackageId(movie.getAPackage().getId());
            movieResponse.setPackageName(movie.getAPackage().getPackageName());
            movieResponse.setPackagePrice(movie.getAPackage().getPrice());
            movieResponse.setViews(movie.getViews());
            movieResponse.setStoryLine(movie.getStoryline());
            movieResponse.setPoster(movie.getPoster());
            movieResponse.setImage(movie.getImage());
            movieResponse.setTrailer(movie.getTrailer());
            movieResponse.setDuration(movie.getDuration());
            movieResponse.setReleaseYear(movie.getReleaseYear());
            movieResponse.setCountry(movie.getCountry().getName());
            movieResponse.setStudio(movie.getStudio().getName());
            movieResponse.setVideoMode(movie.getVideoMode().getCode());
            movieResponse.setClassification("[" + movie.getClassification().getCode() + "] " + movie.getClassification().getName());
            movieResponse.setCategory(movie.getCategory().getName());
            movieResponse.setRating(rating);
            movieResponse.setUserRating(userRating);
            movieResponse.setRatingCount(ratingCount);
            movieResponse.setReviewCount(reviewCount);

            List<MovieGenre> movieGenres = movieGenreService.getMovieGenresByMovieId(movie.getId());
            List<GenreResponse> genres = new ArrayList<>();
            for (MovieGenre movieGenre : movieGenres) {
                GenreResponse genreResponse = new GenreResponse();
                genreResponse.setId(movieGenre.getGenre().getId());
                genreResponse.setName(movieGenre.getGenre().getName());
                genreResponse.setSlug(movieGenre.getGenre().getSlug());
                genres.add(genreResponse);
            }
            movieResponse.setGenres(genres);

            List<MovieLanguage> movieLanguages = movieLanguageService.getMovieLanguagesByMovieId(movie.getId());
            List<String> languageResponses = new ArrayList<>();
            for (MovieLanguage movieLanguage : movieLanguages) {
                languageResponses.add(movieLanguage.getLanguage().getNativeName());
            }
            movieResponse.setLanguages(languageResponses);

            List<MovieFile> movieFiles = movieFileService.getMovieFilesByMovieId(movie.getId());
            List<MovieFileResponse> movieFileResponses = new ArrayList<>();

            for (MovieFile movieFile : movieFiles) {
                MovieFileResponse movieFileResponse = new MovieFileResponse();
                movieFileResponse.setId(movieFile.getId());
                movieFileResponse.setTitle(movieFile.getTitle());
                movieFileResponse.setFileName(movieFile.getFileName());
                movieFileResponse.setThumbnail(movieFile.getThumbnail());
                movieFileResponses.add(movieFileResponse);
            }
            movieResponse.setFiles(movieFileResponses);

            List<Cast> casts = castService.getMainCastsByMovieId(movie.getId());
            List<CastResponse> castResponses = new ArrayList<>();
            for (Cast cast : casts) {
                CastResponse castResponse = new CastResponse();
                castResponse.setId(cast.getId());
                castResponse.setActorName(cast.getActorName());
                castResponse.setCharacterName(cast.getCharacterName());
                castResponse.setMain(cast.isMain());
                castResponses.add(castResponse);
            }
            movieResponse.setMainCasts(castResponses);

            CrewMember director = crewMemberService.getDirectorByMovieId(movie.getId());
            if (director != null) {
                movieResponse.setDirector(director.getName());
            }

            return ResponseEntity.ok(movieResponse);

        }catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/client/check-user-rights/{id}")
    public boolean checkUserRight(
            @PathVariable Long id
    ) {
        try {
            UserDetailsImp userDetails = (UserDetailsImp) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            Long userId = userDetails.getId();

            Movie movie = movieService.getMovieById(id);
            if (movie == null) {
                return false;
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
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            return false;
        }
    }

    @PostMapping( "/create")
    @HasFunctionAccessToFunction(MOVIE_MANAGEMENT_FUNCTION)
    public ResponseEntity<?> createMovie(
            @RequestParam("title") String title,
            @RequestParam("originalTitle") String originalTitle,
            @RequestParam("slug") String slug,
            @RequestParam("canRent") Boolean canRent,
            @RequestParam("price") Double price,
            @RequestParam("packageId") Long packageId,
            @RequestParam("storyline") String storyline,
            @RequestParam("poster") MultipartFile poster,
            @RequestParam("image") MultipartFile image,
            @RequestParam("trailer") String trailer,
            @RequestParam("duration") Integer duration,
            @RequestParam("releaseYear") Integer releaseYear,
            @RequestParam("countryId") Long countryId,
            @RequestParam("studioId") Long studioId,
            @RequestParam("videoModeId") Long videoModeId,
            @RequestParam("classificationId") Long classificationId,
            @RequestParam("categoryId") Long categoryId,
            @RequestParam("isShow") Boolean isShow,
            @RequestParam("isShowAtHome") Boolean isShowAtHome,
            @RequestParam("genreIds") String genreIdsJson,
            @RequestParam("movieFiles") List<MultipartFile> movieFiles,
            @RequestParam("thumbnails") List<MultipartFile> thumbnails,
            @RequestParam("languageIds") String languageIdsJson,
            @RequestParam("casts") String castsJson,
            @RequestParam("crewMembers") String crewJson
    ) {
        try {
//            if (result.hasErrors()) {
//                List<String> errorMessages = result.getAllErrors().stream()
//                        .map(ObjectError::getDefaultMessage)
//                        .collect(Collectors.toList());
//                return ResponseEntity.badRequest().body(errorMessages);
//            }

            Movie newMovie = new Movie();

            newMovie.setTitle(title);
            newMovie.setOriginalTitle(originalTitle);
            newMovie.setSlug(slug);
            newMovie.setCanRent(canRent);
            newMovie.setPrice(price);

            Package aPackage = packageService.getPackageById(packageId);
            if (aPackage == null) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(PACKAGE_NOT_FOUND_MESSAGE);
            }
            newMovie.setAPackage(aPackage);

            newMovie.setViews(0L);
            System.out.println("Length of storyline: " + storyline.length());
            newMovie.setStoryline(storyline);

            //Handle poster
            if (poster != null) {
                MultipartFile posterFile = poster;
                File uploadDir = new File(DEFAULT_UPLOAD_IMAGE_DIR);
                if (!uploadDir.exists()) {
                    uploadDir.mkdirs();
                }

                String posterOriginalFilename = posterFile.getOriginalFilename();
                String posterFileExtension = posterOriginalFilename.substring(posterOriginalFilename.lastIndexOf("."));
                String posterUniqFilename = System.currentTimeMillis() + posterFileExtension;
                String posterPath = uploadDir.getAbsolutePath() + "/" + posterUniqFilename;
                Files.copy(posterFile.getInputStream(), Paths.get(posterPath), StandardCopyOption.REPLACE_EXISTING);
                newMovie.setPoster(posterUniqFilename);
            } else {
                newMovie.setPoster(DEFAULT_POSTER);
            }

            //Handle image
            if (image != null) {
                MultipartFile imageFile = image;
                File uploadDir = new File(DEFAULT_UPLOAD_IMAGE_DIR);
                if (!uploadDir.exists()) {
                    uploadDir.mkdirs();
                }

                String imageOriginalFilename = imageFile.getOriginalFilename();
                String imageFileExtension = imageOriginalFilename.substring(imageOriginalFilename.lastIndexOf("."));
                String imageUniqFilename = System.currentTimeMillis() + imageFileExtension;
                String imagePath = uploadDir.getAbsolutePath() + "/" + imageUniqFilename;
                Files.copy(imageFile.getInputStream(), Paths.get(imagePath), StandardCopyOption.REPLACE_EXISTING);
                newMovie.setImage(imageUniqFilename);
            } else {
                newMovie.setImage(DEFAULT_IMAGE);
            }

            newMovie.setTrailer(trailer);
            newMovie.setDuration(duration);
            newMovie.setReleaseYear(releaseYear);

            Country country = countryService.getCountryById(countryId);
            if (country == null) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(COUNTRY_NOT_FOUND_MESSAGE);
            }
            newMovie.setCountry(country);

            Studio studio = studioService.getStudioById(studioId);
            if (studio == null) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(STUDIO_NOT_FOUND_MESSAGE);
            }
            newMovie.setStudio(studio);

            VideoMode videoMode = videoModeService.getVideoModeById(videoModeId);
            if (videoMode == null) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(VIDEO_MODE_NOT_FOUND_MESSAGE);
            }
            newMovie.setVideoMode(videoMode);

            Classification classification = classificationService.getClassificationById(classificationId);
            if (classification == null) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(CLASSIFICATION_NOT_FOUND_MESSAGE);
            }
            newMovie.setClassification(classification);

            Category category = categoryService.getCategoryById(categoryId);
            if (category == null) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(CATEGORY_NOT_FOUND_MESSAGE);
            }
            newMovie.setCategory(category);

            AdminDetailsImp adminDetails = (AdminDetailsImp) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            Admin admin = adminService.getAdminById(adminDetails.getId());
            if (admin == null){
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new RequestResponse(
                        HttpStatus.NOT_FOUND.value(),
                        ERROR_OCCURRED_MESSAGE
                ));
            }
            newMovie.setAdminCreated(admin);
            newMovie.setAdminModified(admin);

            newMovie.setShow(isShow);
            newMovie.setShowAtHome(isShowAtHome);
            newMovie.setDeleteFlag(DEFAULT_DELETE_FLAG);
            newMovie.setCreatedAt(CURRENT_TIME);
            newMovie.setModifiedAt(CURRENT_TIME);

            movieService.createMovie(newMovie);

            ObjectMapper objectMapper = new ObjectMapper();

            //Handle genres
            List<Long> genreIds = objectMapper.readValue(genreIdsJson, new TypeReference<List<Long>>() {});
            for (Long genreId : genreIds) {
                Genre genre = genreService.getGenreById(genreId);
                if (genre == null) {
                    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(GENRE_NOT_FOUND_MESSAGE);
                }
                MovieGenre movieGenre = new MovieGenre();
                movieGenre.setMovie(newMovie);
                movieGenre.setGenre(genre);
                movieGenre.setDeleteFlag(DEFAULT_DELETE_FLAG);
                movieGenre.setCreatedAt(CURRENT_TIME);
                movieGenre.setModifiedAt(CURRENT_TIME);

                movieGenreService.createMovieGenre(movieGenre);
            }

            //Handle languages
            List<Long> languageIds = objectMapper.readValue(languageIdsJson, new TypeReference<List<Long>>() {});
            for (Long languageId : languageIds) {
                Language language = languageService.getLanguageById(languageId);
                if (language == null) {
                    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(LANGUAGE_NOT_FOUND_MESSAGE);
                }
                MovieLanguage movieLanguage = new MovieLanguage();
                movieLanguage.setMovie(newMovie);
                movieLanguage.setLanguage(language);
                movieLanguage.setDeleteFlag(DEFAULT_DELETE_FLAG);
                movieLanguage.setCreatedAt(CURRENT_TIME);
                movieLanguage.setModifiedAt(CURRENT_TIME);

                movieLanguageService.createMovieLanguage(movieLanguage);
            }

            //Handle MovieFiles
            List<MovieFileRequest> movieFileRequests = new ArrayList<>();

            for (int i = 0; i < movieFiles.size(); i++) {
                MovieFileRequest movieFileRequest = new MovieFileRequest();
                movieFileRequest.setFile(movieFiles.get(i));
                movieFileRequest.setThumbnail(thumbnails.get(i));
                movieFileRequest.setTitle("Episode " + (i + 1));
                movieFileRequests.add(movieFileRequest);
            }

            for (MovieFileRequest movieFileRequest : movieFileRequests) {
                MovieFile movieFile = new MovieFile();
                if (movieFileRequest.getFile() != null) {
                    MultipartFile file = movieFileRequest.getFile();
                    File uploadDir = new File(DEFAULT_UPLOAD_VIDEO_DIR);
                    if (!uploadDir.exists()) {
                        uploadDir.mkdirs();
                    }

                    String fileOriginalFilename = file.getOriginalFilename();
                    String fileExtension = fileOriginalFilename.substring(fileOriginalFilename.lastIndexOf("."));
                    String fileUniqFilename = System.currentTimeMillis() + fileExtension;
                    String filePath = uploadDir.getAbsolutePath() + "/" + fileUniqFilename;
                    Files.copy(file.getInputStream(), Paths.get(filePath), StandardCopyOption.REPLACE_EXISTING);
                    movieFile.setFileName(fileUniqFilename);
                } else {
                    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(MOVIE_FILE_NOT_FOUND_MESSAGE);
                }

                if (movieFileRequest.getThumbnail() != null) {
                    MultipartFile thumbnail = movieFileRequest.getThumbnail();
                    File uploadDir = new File(DEFAULT_UPLOAD_IMAGE_DIR);
                    if (!uploadDir.exists()) {
                        uploadDir.mkdirs();
                    }

                    String thumbnailOriginalFilename = thumbnail.getOriginalFilename();
                    String thumbnailFileExtension = thumbnailOriginalFilename.substring(thumbnailOriginalFilename.lastIndexOf("."));
                    String thumbnailUniqFilename = System.currentTimeMillis() + thumbnailFileExtension;
                    String thumbnailPath = uploadDir.getAbsolutePath() + "/" + thumbnailUniqFilename;
                    Files.copy(thumbnail.getInputStream(), Paths.get(thumbnailPath), StandardCopyOption.REPLACE_EXISTING);
                    movieFile.setThumbnail(thumbnailUniqFilename);
                } else {
                    movieFile.setThumbnail(DEFAULT_THUMBNAIL);
                }

                movieFile.setTitle(movieFileRequest.getTitle());
                movieFile.setMovie(newMovie);
                movieFile.setDeleteFlag(DEFAULT_DELETE_FLAG);
                movieFile.setCreatedAt(CURRENT_TIME);
                movieFile.setModifiedAt(CURRENT_TIME);

                movieFileService.createMovieFile(movieFile);
            }

            //Handle Casts
            List<CastRequest> castRequests = objectMapper.readValue(castsJson, new TypeReference<List<CastRequest>>() {});
            for (CastRequest castRequest : castRequests) {
                Cast cast = new Cast();
                cast.setMovie(newMovie);
                cast.setActorName(castRequest.getActorName());
                cast.setCharacterName(castRequest.getCharacterName());
                cast.setMain(castRequest.isMain());
                cast.setDeleteFlag(DEFAULT_DELETE_FLAG);
                cast.setCreatedAt(CURRENT_TIME);
                cast.setModifiedAt(CURRENT_TIME);

                castService.createCast(cast);
            }

            //Handle CrewMembers
            List<CrewMemberRequest> crewMemberRequests = objectMapper.readValue(crewJson, new TypeReference<List<CrewMemberRequest>>() {});
            for (CrewMemberRequest crewMemberRequest : crewMemberRequests) {
                CrewMember crewMember = new CrewMember();

                crewMember.setName(crewMemberRequest.getName());

                CrewPosition crewPosition = crewPositionService.getCrewPositionById(crewMemberRequest.getCrewPositionId());
                if (crewPosition == null) {
                    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(CREW_POSITION_NOT_FOUND_MESSAGE);
                }
                crewMember.setCrewPosition(crewPosition);

                crewMember.setMovie(newMovie);
                crewMember.setDeleteFlag(DEFAULT_DELETE_FLAG);
                crewMember.setCreatedAt(CURRENT_TIME);
                crewMember.setModifiedAt(CURRENT_TIME);

                crewMemberService.createCrewMember(crewMember);
            }

            return ResponseEntity.status(HttpStatus.CREATED).body(new RequestResponse(
                    HttpStatus.CREATED.value(),
                    CREATE_MOVIE_SUCCESS
            ));
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/update")
    @HasFunctionAccessToFunction(MOVIE_MANAGEMENT_FUNCTION)
    public ResponseEntity<?> updateMovie(
            @Valid
            @RequestParam("id") Long id,
            @RequestParam("title") String title,
            @RequestParam("originalTitle") String originalTitle,
            @RequestParam("slug") String slug,
            @RequestParam("canRent") Boolean canRent,
            @RequestParam("price") Double price,
            @RequestParam("packageId") Long packageId,
            @RequestParam("storyline") String storyline,
            @RequestParam(value = "poster", required = false) MultipartFile poster,
            @RequestParam("trailer") String trailer,
            @RequestParam("duration") Integer duration,
            @RequestParam("releaseYear") Integer releaseYear,
            @RequestParam("countryId") Long countryId,
            @RequestParam("studioId") Long studioId,
            @RequestParam("videoModeId") Long videoModeId,
            @RequestParam("classificationId") Long classificationId,
            @RequestParam("isShow") Boolean isShow,
            @RequestParam("isShowAtHome") Boolean isShowAtHome,
            @RequestParam("genreIds") String genreIdsJson,
            @RequestParam("movieFiles") List<MultipartFile> movieFiles,
            @RequestParam("thumbnails") List<MultipartFile> thumbnails,
            @RequestParam("languageIds") String languageIdsJson,
            @RequestParam("casts") String castsJson,
            @RequestParam("crewMembers") String crewJson,
            BindingResult result
    ){
        try {
            if (result.hasErrors()) {
                List<String> errorMessages = result.getAllErrors().stream()
                        .map(ObjectError::getDefaultMessage)
                        .collect(Collectors.toList());
                return ResponseEntity.badRequest().body(errorMessages);
            }

            Movie updateMovie = movieService.getMovieById(id);
            if (updateMovie == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(MOVIE_NOT_FOUND_MESSAGE);
            }
            if (title != null) {
                updateMovie.setTitle(title);
            }
            if (originalTitle != null) {
                updateMovie.setOriginalTitle(originalTitle);
            }
            if (slug != null) {
                updateMovie.setSlug(slug);
            }
            if (canRent != null) {
                updateMovie.setCanRent(canRent);
            }
            if (price != null) {
                updateMovie.setPrice(price);
            }
            if (packageId != null) {
                Package aPackage = packageService.getPackageById(packageId);
                if (aPackage == null) {
                    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(PACKAGE_NOT_FOUND_MESSAGE);
                }
                updateMovie.setAPackage(aPackage);
            }
            if (storyline != null) {
                updateMovie.setStoryline(storyline);
            }
            if (poster != null) {
                MultipartFile posterFile = poster;
                File uploadDir = new File(DEFAULT_UPLOAD_IMAGE_DIR);
                if (!uploadDir.exists()) {
                    uploadDir.mkdirs();
                }

                if (updateMovie.getPoster() != null) {
                    File oldPoster = new File(uploadDir.getAbsolutePath() + "/" + updateMovie.getPoster());
                    if (oldPoster.exists()) {
                        oldPoster.delete();
                    }
                }

                String posterOriginalFilename = posterFile.getOriginalFilename();
                String posterFileExtension = posterOriginalFilename.substring(posterOriginalFilename.lastIndexOf("."));
                String posterUniqFilename = System.currentTimeMillis() + posterFileExtension;
                String posterPath = uploadDir.getAbsolutePath() + "/" + posterUniqFilename;
                Files.copy(posterFile.getInputStream(), Paths.get(posterPath), StandardCopyOption.REPLACE_EXISTING);
                updateMovie.setPoster(posterUniqFilename);
            }
            if (trailer != null) {
                updateMovie.setTrailer(trailer);
            }

            if (duration != null) {
                updateMovie.setDuration(duration);
            }
            if (releaseYear != null) {
                updateMovie.setReleaseYear(releaseYear);
            }

            if (countryId != null) {
                Country country = countryService.getCountryById(countryId);
                if (country == null) {
                    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(COUNTRY_NOT_FOUND_MESSAGE);
                }
                updateMovie.setCountry(country);
            }

            if (studioId != null) {
                Studio studio = studioService.getStudioById(studioId);
                if (studio == null) {
                    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(STUDIO_NOT_FOUND_MESSAGE);
                }
                updateMovie.setStudio(studio);
            }

            if (videoModeId != null) {
                VideoMode videoMode = videoModeService.getVideoModeById(videoModeId);
                if (videoMode == null) {
                    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(VIDEO_MODE_NOT_FOUND_MESSAGE);
                }
                updateMovie.setVideoMode(videoMode);
            }

            if (classificationId != null) {
                Classification classification = classificationService.getClassificationById(classificationId);
                if (classification == null) {
                    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(CLASSIFICATION_NOT_FOUND_MESSAGE);
                }
                updateMovie.setClassification(classification);
            }

            AdminDetailsImp adminDetails = (AdminDetailsImp) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            Admin admin = adminService.getAdminById(adminDetails.getId());
            if (admin == null){
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new RequestResponse(
                        HttpStatus.NOT_FOUND.value(),
                        ERROR_OCCURRED_MESSAGE
                ));
            }
            updateMovie.setAdminModified(admin);

            if (isShow != null) {
                updateMovie.setShow(isShow);
            }

            if (isShowAtHome != null) {
                updateMovie.setShowAtHome(isShowAtHome);
            }

            updateMovie.setModifiedAt(CURRENT_TIME);

            movieService.updateMovie(updateMovie);

            ObjectMapper objectMapper = new ObjectMapper();

            //Handle genres
            if (genreIdsJson != null) {
                List<Long> genreIds = objectMapper.readValue(genreIdsJson, new TypeReference<List<Long>>() {});
                for (Long genreId : genreIds) {
                    Genre genre = genreService.getGenreById(genreId);
                    if (genre == null) {
                        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(GENRE_NOT_FOUND_MESSAGE);
                    }
                    MovieGenre movieGenre = movieGenreService.getMovieGenreByMovieIdAndGenreId(updateMovie.getId(), genreId);
                    if (movieGenre == null) {
                        movieGenre = new MovieGenre();
                        movieGenre.setMovie(updateMovie);
                        movieGenre.setGenre(genre);
                        movieGenre.setDeleteFlag(DEFAULT_DELETE_FLAG);
                        movieGenre.setCreatedAt(CURRENT_TIME);
                        movieGenre.setModifiedAt(CURRENT_TIME);

                        movieGenreService.createMovieGenre(movieGenre);
                    } else {
                        movieGenre.setDeleteFlag(DEFAULT_DELETE_FLAG);
                        movieGenre.setModifiedAt(CURRENT_TIME);
                        movieGenreService.deleteMovieGenre(movieGenre);
                    }
                }
            }

            //Handle languages
            if (languageIdsJson != null) {
                List<Long> languageIds = objectMapper.readValue(languageIdsJson, new TypeReference<List<Long>>() {});
                for (Long languageId : languageIds) {
                    Language language = languageService.getLanguageById(languageId);
                    if (language == null) {
                        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(LANGUAGE_NOT_FOUND_MESSAGE);
                    }
                    MovieLanguage movieLanguage = movieLanguageService.getMovieLanguageByMovieIdAndLanguageId(updateMovie.getId(), languageId);
                    if (movieLanguage == null) {
                        movieLanguage = new MovieLanguage();
                        movieLanguage.setMovie(updateMovie);
                        movieLanguage.setLanguage(language);
                        movieLanguage.setDeleteFlag(DEFAULT_DELETE_FLAG);
                        movieLanguage.setCreatedAt(CURRENT_TIME);
                        movieLanguage.setModifiedAt(CURRENT_TIME);

                        movieLanguageService.createMovieLanguage(movieLanguage);
                    } else {
                        movieLanguage.setDeleteFlag(DEFAULT_DELETE_FLAG);
                        movieLanguage.setModifiedAt(CURRENT_TIME);
                        movieLanguageService.deleteMovieLanguage(movieLanguage);
                    }
                }
            }

            //Handle MovieFiles
            if (movieFiles != null){
                List<MovieFileRequest> movieFileRequests = new ArrayList<>();


            }

            //Handle Casts
            if (castsJson != null) {
                List<CastRequest> castRequests = objectMapper.readValue(castsJson, new TypeReference<List<CastRequest>>() {});

            }

            //Handle CrewMembers
            if (crewJson != null) {
                List<CrewMemberRequest> crewMemberRequests = objectMapper.readValue(crewJson, new TypeReference<List<CrewMemberRequest>>() {});

            }

            return ResponseEntity.status(HttpStatus.OK).body(new RequestResponse(
                    HttpStatus.OK.value(),
                    UPDATE_MOVIE_SUCCESS
            ));
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/update/show")
    @HasFunctionAccessToFunction(MOVIE_MANAGEMENT_FUNCTION)
    public ResponseEntity<?> updateShowMovie(
            @RequestParam Long id,
            @RequestParam Boolean isShow
    ) {
        try {
            Movie updateMovie = movieService.getMovieById(id);
            if (updateMovie == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(MOVIE_NOT_FOUND_MESSAGE);
            }
            updateMovie.setShow(isShow);
            updateMovie.setModifiedAt(CURRENT_TIME);

            movieService.updateMovie(updateMovie);

            return ResponseEntity.status(HttpStatus.OK).body(new RequestResponse(
                    HttpStatus.OK.value(),
                    UPDATE_MOVIE_SUCCESS
            ));
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/update/showAtHome")
    @HasFunctionAccessToFunction(MOVIE_MANAGEMENT_FUNCTION)
    public ResponseEntity<?> updateShowAtHomeMovie(
            @RequestParam Long id,
            @RequestParam Boolean isShowAtHome
    ) {
        try {
            Movie updateMovie = movieService.getMovieById(id);
            if (updateMovie == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(MOVIE_NOT_FOUND_MESSAGE);
            }
            updateMovie.setShowAtHome(isShowAtHome);
            updateMovie.setModifiedAt(CURRENT_TIME);

            movieService.updateMovie(updateMovie);

            return ResponseEntity.status(HttpStatus.OK).body(new RequestResponse(
                    HttpStatus.OK.value(),
                    UPDATE_MOVIE_SUCCESS
            ));
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/update/views")
    public ResponseEntity<?> updateViews(@RequestParam Long id) {
        try {
            Movie updateMovie = movieService.getMovieById(id);
            if (updateMovie == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(MOVIE_NOT_FOUND_MESSAGE);
            }
            updateMovie.setViews(updateMovie.getViews() + 1);
            updateMovie.setModifiedAt(CURRENT_TIME);

            movieService.updateMovie(updateMovie);

            return ResponseEntity.status(HttpStatus.OK).body(new RequestResponse(
                    HttpStatus.OK.value(),
                    UPDATE_MOVIE_SUCCESS
            ));
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/delete/{id}")
    @HasFunctionAccessToFunction(MOVIE_MANAGEMENT_FUNCTION)
    public ResponseEntity<?> deleteMovie(@PathVariable Long id) {
        try {
            Movie deleteMovie = movieService.getMovieById(id);
            if (deleteMovie == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(MOVIE_NOT_FOUND_MESSAGE);
            }
            deleteMovie.setDeleteFlag(true);
            deleteMovie.setModifiedAt(CURRENT_TIME);

            movieService.updateMovie(deleteMovie);

            return ResponseEntity.status(HttpStatus.OK).body(new RequestResponse(
                    HttpStatus.OK.value(),
                    DELETE_MOVIE_SUCCESS
            ));
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
