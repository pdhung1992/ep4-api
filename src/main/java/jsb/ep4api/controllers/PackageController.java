package jsb.ep4api.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import jsb.ep4api.config.HasFunctionAccessToFunction;
import jsb.ep4api.entities.Package;
import jsb.ep4api.payloads.requests.PackageRequest;
import jsb.ep4api.payloads.responses.PackageResponse;
import jsb.ep4api.payloads.responses.RequestResponse;
import jsb.ep4api.payloads.responses.SpecResponse;
import jsb.ep4api.services.PackageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

import static jsb.ep4api.constants.Constants.*;

@RestController
@RequestMapping("/api/packages")
public class PackageController {
    @Autowired
    private PackageService packageService;

    @GetMapping
    public ResponseEntity<?> getAllPackages(
            @RequestParam(required = false) Integer pageNo,
            @RequestParam(required = false) Integer pageSize,
            @RequestParam(required = false) String sortField,
            @RequestParam(required = false) String sortDir,
            @RequestParam(required = false) String name
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
            Page<Package> packages = packageService.getPackages(pageNo, pageSize, sortField, sortDir, name);
            List<Package> packageList = packages.getContent();
            List<PackageResponse> packageResponses = new ArrayList<>();

            if (!packageList.isEmpty()) {
                for (Package p : packageList) {
                    PackageResponse packageResponse = new PackageResponse();
                    packageResponse.setId(p.getId());
                    packageResponse.setName(p.getPackageName());
                    packageResponse.setPrice(p.getPrice());
                    packageResponse.setExpirationUnit(p.getExpirationUnit());
                    packageResponse.setDescription(p.getDescription());
                    packageResponse.setSlug(p.getSlug());
                    packageResponses.add(packageResponse);
                }
            }

            int totalPages = packages.getTotalPages();
            long totalItems = packages.getTotalElements();
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
            specResponse.setData(packageResponses);

            return ResponseEntity.ok(specResponse);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/select")
    public ResponseEntity<?> getAllPackagesSelect() {
        try {
            List<Package> packages = packageService.getPackagesSelect();
            List<PackageResponse> packageResponses = new ArrayList<>();

            if (!packages.isEmpty()) {
                for (Package p : packages) {
                    PackageResponse packageResponse = new PackageResponse();
                    packageResponse.setId(p.getId());
                    packageResponse.setName(p.getPackageName());
                    packageResponses.add(packageResponse);
                }
            }

            return ResponseEntity.ok(packageResponses);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/create")
    @HasFunctionAccessToFunction(MOVIE_MANAGEMENT_FUNCTION)
    public ResponseEntity<?> createPackage(@RequestBody PackageRequest createPackageRequest) {
        try {
            Package newPackage = new Package();

            newPackage.setPackageName(createPackageRequest.getName());
            newPackage.setPrice(createPackageRequest.getPrice());
            newPackage.setExpirationUnit(createPackageRequest.getExpirationUnit());
            newPackage.setDescription(createPackageRequest.getDescription());
            newPackage.setSlug(createPackageRequest.getSlug());
            newPackage.setCreatedAt(CURRENT_TIME);
            newPackage.setModifiedAt(CURRENT_TIME);
            newPackage.setDeleteFlag(DEFAULT_DELETE_FLAG);

            packageService.createPackage(newPackage);

            return ResponseEntity.status(HttpStatus.CREATED).body(new RequestResponse(
                    HttpStatus.CREATED.value(),
                    CREATE_PACKAGE_SUCCESS_MESSAGE
            ));

        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/update")
    @HasFunctionAccessToFunction(MOVIE_MANAGEMENT_FUNCTION)
    public ResponseEntity<?> updatePackage(@RequestBody PackageRequest updatePackageRequest) {
        try {
            Package packageToUpdate = packageService.getPackageById(updatePackageRequest.getId());

            packageToUpdate.setPackageName(updatePackageRequest.getName());
            packageToUpdate.setPrice(updatePackageRequest.getPrice());
            packageToUpdate.setExpirationUnit(updatePackageRequest.getExpirationUnit());
            packageToUpdate.setDescription(updatePackageRequest.getDescription());
            packageToUpdate.setSlug(updatePackageRequest.getSlug());
            packageToUpdate.setModifiedAt(CURRENT_TIME);

            packageService.updatePackage(packageToUpdate);

            return ResponseEntity.status(HttpStatus.OK).body(new RequestResponse(
                    HttpStatus.OK.value(),
                    UPDATE_PACKAGE_SUCCESS_MESSAGE
            ));

        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/delete/{id}")
    @HasFunctionAccessToFunction(MOVIE_MANAGEMENT_FUNCTION)
    public ResponseEntity<?> deletePackage(@PathVariable Long id) {
        try {
            Package packageToDelete = packageService.getPackageById(id);

            packageToDelete.setDeleteFlag(true);
            packageToDelete.setModifiedAt(CURRENT_TIME);

            packageService.deletePackage(packageToDelete);

            return ResponseEntity.status(HttpStatus.OK).body(new RequestResponse(
                    HttpStatus.OK.value(),
                    DELETE_PACKAGE_SUCCESS_MESSAGE
            ));

        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
