package jsb.ep4api.controllers;

import jsb.ep4api.entities.UserPackage;
import jsb.ep4api.payloads.responses.SpecResponse;
import jsb.ep4api.payloads.responses.UserPackageResponse;
import jsb.ep4api.securities.service.UserDetailsImp;
import jsb.ep4api.services.UserPackageService;
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
@RequestMapping("/api/user-package")
public class UserPackageController {
    @Autowired
    private UserPackageService userPackageService;

    @GetMapping
    public ResponseEntity<?> getPackagesByUser(
            @RequestParam(required = false) Integer pageNo,
            @RequestParam(required = false) Integer pageSize,
            @RequestParam(required = false) String sortField,
            @RequestParam(required = false) String sortDir,
            @RequestParam(required = false) Boolean isExpired
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

            Page<UserPackage> userPackages = userPackageService.getUserPackagesByUserId(userId, isExpired, pageNo, pageSize, sortField, sortDir);
            List<UserPackage> userPackageList = userPackages.getContent();
            List<UserPackageResponse> responses = new ArrayList<>();

            if (!userPackageList.isEmpty()) {
                for (UserPackage userPackage : userPackageList) {
                    UserPackageResponse response = new UserPackageResponse();
                    response.setId(userPackage.getId());
                    response.setPackageId(userPackage.getAPackage().getId());
                    response.setPackageName(userPackage.getAPackage().getPackageName());
                    response.setPackagePrice(userPackage.getAPackage().getPrice());
                    response.setSlug(userPackage.getAPackage().getSlug());
                    response.setExpiredAt(userPackage.getExpiredAt());

                    responses.add(response);
                }
            }

            int totalPages = userPackages.getTotalPages();
            long totalItems = userPackages.getTotalElements();
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
