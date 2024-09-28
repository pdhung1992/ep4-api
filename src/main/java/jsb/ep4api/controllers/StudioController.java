package jsb.ep4api.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import jsb.ep4api.config.HasFunctionAccessToFunction;
import jsb.ep4api.entities.Country;
import jsb.ep4api.entities.Studio;
import jsb.ep4api.payloads.requests.StudioRequest;
import jsb.ep4api.payloads.responses.RequestResponse;
import jsb.ep4api.payloads.responses.SpecResponse;
import jsb.ep4api.payloads.responses.StudioResponse;
import jsb.ep4api.services.CountryService;
import jsb.ep4api.services.StudioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
@RequestMapping("/api/studios")
public class StudioController {
    @Autowired
    private StudioService studioService;
    @Autowired
    CountryService countryService;

    @GetMapping
    public ResponseEntity<?> getStudios(
            @RequestParam(required = false) Integer pageNo,
            @RequestParam(required = false) Integer pageSize,
            @RequestParam(required = false) String sortField,
            @RequestParam(required = false) String sortDir,
            @RequestParam(required = false) String name,
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
            Page<Studio> studios = studioService.getStudios(pageNo, pageSize, sortField, sortDir, name, countryId);
            List<Studio> studioList = studios.getContent();
            List<StudioResponse> studioResponses = new ArrayList<>();

            if (!studioList.isEmpty()) {
                for (Studio studio : studioList) {
                    StudioResponse studioResponse = new StudioResponse();
                    studioResponse.setId(studio.getId());
                    studioResponse.setName(studio.getName());
                    studioResponse.setDescription(studio.getDescription());
                    studioResponse.setLogo(studio.getLogo());
                    studioResponse.setBanner(studio.getBanner());
                    studioResponse.setWebsite(studio.getWebsite());
                    studioResponse.setAddress(studio.getAddress());
                    studioResponse.setCountry(studio.getCountry().getName());
                    studioResponse.setCountryId(studio.getCountry().getId());
                    studioResponse.setEstablishedYear(studio.getEstablishedYear());
                    studioResponse.setSlug(studio.getSlug());
                    studioResponses.add(studioResponse);
                }
            }

            int totalPages = studios.getTotalPages();
            long totalItems = studios.getTotalElements();
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
            specResponse.setData(studioResponses);

            return ResponseEntity.ok(specResponse);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/select")
    public ResponseEntity<?> getStudiosSelect() {
        try {
            List<Studio> studios = studioService.getStudiosSelect();
            List<StudioResponse> studioResponses = new ArrayList<>();

            if (!studios.isEmpty()) {
                for (Studio studio : studios) {
                    StudioResponse studioResponse = new StudioResponse();
                    studioResponse.setId(studio.getId());
                    studioResponse.setName(studio.getName());
                    studioResponses.add(studioResponse);
                }
            }

            return ResponseEntity.ok(studioResponses);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/create")
    @HasFunctionAccessToFunction(MOVIE_MANAGEMENT_FUNCTION)
    public ResponseEntity<?> createStudio(@ModelAttribute StudioRequest createStudioRequest) {
        try {
            Country country = countryService.getCountryById(createStudioRequest.getCountryId());
            if (country == null) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(COUNTRY_NOT_FOUND_MESSAGE);
            }

            Studio studio = new Studio();

            if (createStudioRequest.getLogo() != null) {
                MultipartFile logo = createStudioRequest.getLogo();
                File uploadDir = new File(DEFAULT_UPLOAD_IMAGE_DIR);
                if (!uploadDir.exists()) {
                    uploadDir.mkdirs();
                }

                String logoOriginalFilename = logo.getOriginalFilename();
                String logoFileExtension = logoOriginalFilename.substring(logoOriginalFilename.lastIndexOf("."));
                String logoUniqFilename = System.currentTimeMillis() + logoFileExtension;
                String logoPath = uploadDir.getAbsolutePath() + "/" + logoUniqFilename;
                Files.copy(logo.getInputStream(), Paths.get(logoPath), StandardCopyOption.REPLACE_EXISTING);
                studio.setLogo(logoUniqFilename);
            } else {
                studio.setLogo(DEFAULT_LOGO);
            }

            if (createStudioRequest.getBanner() != null) {
                MultipartFile banner = createStudioRequest.getBanner();
                File uploadDir = new File(DEFAULT_UPLOAD_IMAGE_DIR);
                if (!uploadDir.exists()) {
                    uploadDir.mkdirs();
                }

                String bannerOriginalFilename = banner.getOriginalFilename();
                String bannerFileExtension = bannerOriginalFilename.substring(bannerOriginalFilename.lastIndexOf("."));
                String bannerUniqFilename = System.currentTimeMillis() + bannerFileExtension;
                String bannerPath = uploadDir.getAbsolutePath() + "/" + bannerUniqFilename;
                Files.copy(banner.getInputStream(), Paths.get(bannerPath), StandardCopyOption.REPLACE_EXISTING);
                studio.setBanner(bannerUniqFilename);
            } else {
                studio.setBanner(DEFAULT_BANNER);
            }

            studio.setName(createStudioRequest.getName());
            studio.setDescription(createStudioRequest.getDescription());
            studio.setWebsite(createStudioRequest.getWebsite());
            studio.setAddress(createStudioRequest.getAddress());
            studio.setCountry(country);
            studio.setEstablishedYear(createStudioRequest.getEstablishedYear());
            studio.setSlug(createStudioRequest.getSlug());
            studio.setDeleteFlag(DEFAULT_DELETE_FLAG);
            studio.setCreatedAt(CURRENT_TIME);
            studio.setModifiedAt(CURRENT_TIME);

            studioService.createStudio(studio);
            return ResponseEntity.status(HttpStatus.CREATED).body(new RequestResponse(
                    HttpStatus.CREATED.value(),
                    CREATE_STUDIO_SUCCESS_MESSAGE
            ));

        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/update")
    @HasFunctionAccessToFunction(MOVIE_MANAGEMENT_FUNCTION)
    public ResponseEntity<?> updateStudio(@ModelAttribute StudioRequest updateStudioRequest) {
        try {
            Studio updateStudio = studioService.getStudioById(updateStudioRequest.getId());
            if (updateStudio == null) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(STUDIO_NOT_FOUND_MESSAGE);
            }

            Country country = countryService.getCountryById(updateStudioRequest.getCountryId());
            if (country == null) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(COUNTRY_NOT_FOUND_MESSAGE);
            }

            if (updateStudioRequest.getLogo() != null && !updateStudioRequest.getLogo().isEmpty()) {
                MultipartFile logo = updateStudioRequest.getLogo();
                File uploadDir = new File(DEFAULT_UPLOAD_IMAGE_DIR);
                if (!uploadDir.exists()) {
                    uploadDir.mkdirs();
                }

                if(updateStudio.getLogo() != null){
                    File oldLogo = new File(uploadDir, updateStudio.getLogo());
                    oldLogo.delete();
                }

                String logoOriginalFilename = logo.getOriginalFilename();
                String logoFileExtension = logoOriginalFilename.substring(logoOriginalFilename.lastIndexOf("."));
                String logoUniqFilename = System.currentTimeMillis() + logoFileExtension;
                String logoPath = uploadDir.getAbsolutePath() + "/" + logoUniqFilename;
                Files.copy(logo.getInputStream(), Paths.get(logoPath), StandardCopyOption.REPLACE_EXISTING);
                updateStudio.setLogo(logoUniqFilename);
            }

            if (updateStudioRequest.getBanner() != null && !updateStudioRequest.getBanner().isEmpty()) {
                MultipartFile banner = updateStudioRequest.getBanner();
                File uploadDir = new File(DEFAULT_UPLOAD_IMAGE_DIR);
                if (!uploadDir.exists()) {
                    uploadDir.mkdirs();
                }

                if(updateStudio.getBanner() != null){
                    File oldBanner = new File(uploadDir, updateStudio.getBanner());
                    oldBanner.delete();
                }

                String bannerOriginalFilename = banner.getOriginalFilename();
                String bannerFileExtension = bannerOriginalFilename.substring(bannerOriginalFilename.lastIndexOf("."));
                String bannerUniqFilename = System.currentTimeMillis() + bannerFileExtension;
                String bannerPath = uploadDir.getAbsolutePath() + "/" + bannerUniqFilename;
                Files.copy(banner.getInputStream(), Paths.get(bannerPath), StandardCopyOption.REPLACE_EXISTING);
                updateStudio.setBanner(bannerUniqFilename);
            }

            updateStudio.setName(updateStudioRequest.getName());
            updateStudio.setDescription(updateStudioRequest.getDescription());
            updateStudio.setWebsite(updateStudioRequest.getWebsite());
            updateStudio.setAddress(updateStudioRequest.getAddress());
            updateStudio.setCountry(country);
            updateStudio.setEstablishedYear(updateStudioRequest.getEstablishedYear());
            updateStudio.setSlug(updateStudioRequest.getSlug());
            updateStudio.setModifiedAt(CURRENT_TIME);

            studioService.updateStudio(updateStudio);
            return ResponseEntity.status(HttpStatus.OK).body(new RequestResponse(
                    HttpStatus.OK.value(),
                    UPDATE_STUDIO_SUCCESS_MESSAGE
            ));
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/delete/{id}")
    @HasFunctionAccessToFunction(MOVIE_MANAGEMENT_FUNCTION)
    public ResponseEntity<?> deleteStudio(@PathVariable Long id) {
        try {
            Studio deleteStudio = studioService.getStudioById(id);
            if (deleteStudio == null) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(STUDIO_NOT_FOUND_MESSAGE);
            }

            File uploadDir = new File(DEFAULT_UPLOAD_IMAGE_DIR);
            if (!uploadDir.exists()) {
                uploadDir.mkdirs();
            }

            if(deleteStudio.getLogo() != null && !deleteStudio.getLogo().equals(DEFAULT_LOGO)){
                File oldLogo = new File(uploadDir, deleteStudio.getLogo());
                oldLogo.delete();
            }

            if(deleteStudio.getBanner() != null && !deleteStudio.getBanner().equals(DEFAULT_BANNER)){
                File oldBanner = new File(uploadDir, deleteStudio.getBanner());
                oldBanner.delete();
            }

            deleteStudio.setDeleteFlag(true);
            deleteStudio.setModifiedAt(CURRENT_TIME);

            studioService.updateStudio(deleteStudio);
            return ResponseEntity.status(HttpStatus.OK).body(new RequestResponse(
                    HttpStatus.OK.value(),
                    DELETE_STUDIO_SUCCESS_MESSAGE
            ));
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
