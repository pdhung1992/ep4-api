package jsb.ep4api.controllers;

import jsb.ep4api.entities.UserMovie;
import jsb.ep4api.payloads.responses.SpecResponse;
import jsb.ep4api.payloads.responses.UserMovieResponse;
import jsb.ep4api.securities.service.UserDetailsImp;
import jsb.ep4api.services.UserMovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/user-movie")
public class UserMovieController {
    @Autowired
    private UserMovieService userMovieService;

    @GetMapping
    public ResponseEntity<?> getMoviesByUser(
            @RequestParam(required = false) Integer pageNo,
            @RequestParam(required = false) Integer pageSize,
            @RequestParam(required = false) String sortField,
            @RequestParam(required = false) String sortDir,
            @RequestParam(required = false) Boolean isExpired,
            @RequestParam(required = false) String search
    ){
        try {
            if (pageNo == null) {
                pageNo = 1;
            }
            if (pageSize == null) {
                pageSize = 10;
            }
            if (sortField == null) {
                sortField = "expiredAt";
            }
            if (sortDir == null) {
                sortDir = "desc";
            }

            UserDetailsImp userDetailsImp = (UserDetailsImp) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            Long userId = userDetailsImp.getId();

            Page<UserMovie> userMovies = userMovieService.getUserMoviesByUserId(userId, isExpired, pageNo, pageSize, sortField, sortDir, search);
            List<UserMovie> userMovieList = userMovies.getContent();
            List<UserMovieResponse> responses = new ArrayList<>();

            if (!userMovieList.isEmpty()) {
                for (UserMovie userMovie : userMovieList) {
                    UserMovieResponse response = new UserMovieResponse();
                    response.setId(userMovie.getId());
                    response.setMovieId(userMovie.getMovie().getId());
                    response.setMovieTitle(userMovie.getMovie().getTitle());
                    response.setMoviePrice(userMovie.getMovie().getPrice());
                    response.setSlug(userMovie.getMovie().getSlug());
                    response.setExpiredAt(userMovie.getExpiredAt());

                    responses.add(response);
                }
            }

            int totalPages = userMovies.getTotalPages();
            long totalItems = userMovies.getTotalElements();
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
            specResponse.setData(responses);

            return ResponseEntity.ok(specResponse);

        }catch (Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
