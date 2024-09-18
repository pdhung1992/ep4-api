package jsb.ep4api.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import jsb.ep4api.entities.Classification;
import jsb.ep4api.payloads.responses.ClassificationResponse;
import jsb.ep4api.payloads.responses.SpecResponse;
import jsb.ep4api.services.ClassificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/classifications")
public class ClassificationController {
    @Autowired
    private ClassificationService classificationService;

    @GetMapping
    public ResponseEntity<?> getAllClassifications(
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

            Page<Classification> classifications = classificationService.getAllClassifications(pageNo, pageSize, sortField, sortDir, name);
            List<Classification> classificationList = classifications.getContent();
            List<ClassificationResponse> classificationResponses = new ArrayList<>();

            if (!classificationList.isEmpty()) {
                for (Classification c : classificationList) {
                    ClassificationResponse classificationResponse = new ClassificationResponse();
                    classificationResponse.setId(c.getId());
                    classificationResponse.setName(c.getName());
                    classificationResponse.setDescription(c.getDescription());
                    classificationResponse.setCode(c.getCode());
                    classificationResponse.setSlug(c.getSlug());
                    classificationResponses.add(classificationResponse);
                }
            }

            int totalPages = classifications.getTotalPages();
            long totalItems = classifications.getTotalElements();
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
            specResponse.setData(classificationResponses);

            return ResponseEntity.ok(specResponse);

        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
