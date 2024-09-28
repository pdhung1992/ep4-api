package jsb.ep4api.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import jsb.ep4api.entities.*;
import jsb.ep4api.entities.Package;
import jsb.ep4api.payloads.requests.CastRequest;
import jsb.ep4api.payloads.requests.CrewMemberRequest;
import jsb.ep4api.payloads.requests.MovieFileRequest;
import jsb.ep4api.payloads.responses.MovieResponse;
import jsb.ep4api.payloads.responses.RequestResponse;
import jsb.ep4api.payloads.responses.SpecResponse;
import jsb.ep4api.securities.service.AdminDetailsImp;
import jsb.ep4api.services.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;

import static jsb.ep4api.constants.Constants.*;

@RestController
@RequestMapping("/api/movies")
public class MovieController {
    @Autowired
    private MovieService movieService;
    @Autowired
    PackageService packageService;
    @Autowired
    CountryService countryService;
    @Autowired
    StudioService studioService;
    @Autowired
    VideoModeService videoModeService;
    @Autowired
    ClassificationService classificationService;
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

    @GetMapping("/admin")
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

    @GetMapping("/client")
    public ResponseEntity<?> getDisplayMovies(
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
                movieResponse.setTitle(movie.getTitle());
                movieResponse.setOriginalTitle(movie.getOriginalTitle());
                movieResponse.setDuration(movie.getDuration());
                movieResponse.setReleaseYear(movie.getReleaseYear());
                movieResponse.setShow(movie.isShow());
                movieResponse.setShowAtHome(movie.isShowAtHome());
                movieResponse.setPoster(movie.getPoster());

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

    @PostMapping( "/create")
    public ResponseEntity<?> createMovie(
            @RequestParam("title") String title,
            @RequestParam("originalTitle") String originalTitle,
            @RequestParam("slug") String slug,
            @RequestParam("canRent") Boolean canRent,
            @RequestParam("price") Double price,
            @RequestParam("packageId") Long packageId,
            @RequestParam("storyline") String storyline,
            @RequestParam("poster") MultipartFile poster,
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
            @RequestParam("crewMembers") String crewJson
    ) {
        try {
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
            newMovie.setStoryline(storyline);

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
    public ResponseEntity<?> updateMovie(
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
            @RequestParam("crewMembers") String crewJson
    ){
        try {
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

    @DeleteMapping("/delete/{id}")
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
