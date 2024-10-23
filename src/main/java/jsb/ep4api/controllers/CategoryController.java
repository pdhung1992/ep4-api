package jsb.ep4api.controllers;

import jsb.ep4api.entities.Category;
import jsb.ep4api.payloads.responses.CategoryResponse;
import jsb.ep4api.payloads.responses.SpecResponse;
import jsb.ep4api.services.CategoryService;
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
@RequestMapping("/api/categories")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @GetMapping
    ResponseEntity<?> getAllCategories(
            @RequestParam(required = false) Integer pageNo,
            @RequestParam(required = false) Integer pageSize,
            @RequestParam(required = false) String sortField,
            @RequestParam(required = false) String sortDir,
            @RequestParam(required = false) String name
    ) {
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

            Page<Category> categories = categoryService.getAllCategories(pageNo, pageSize, sortField, sortDir, name);
            List<Category> categoryList = categories.getContent();
            List<CategoryResponse> categoryResponses = new ArrayList<>();

            if (!categoryList.isEmpty()) {
                for (Category c : categoryList) {
                    CategoryResponse categoryResponse = new CategoryResponse();
                    categoryResponse.setId(c.getId());
                    categoryResponse.setName(c.getName());
                    categoryResponse.setDescription(c.getDescription());
                    categoryResponse.setSlug(c.getSlug());
                    categoryResponses.add(categoryResponse);
                }
            }

            int totalPages = categories.getTotalPages();
            long totalItems = categories.getTotalElements();
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
            specResponse.setData(categoryResponses);

            return ResponseEntity.ok(specResponse);

        }catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/select")
    ResponseEntity<?> getCategoriesSelect() {
        try {
            List<Category> categories = categoryService.getAllCategoriesSelect();
            List<CategoryResponse> categoryResponses = new ArrayList<>();

            if (!categories.isEmpty()) {
                for (Category c : categories) {
                    CategoryResponse categoryResponse = new CategoryResponse();
                    categoryResponse.setId(c.getId());
                    categoryResponse.setName(c.getName());
                    categoryResponses.add(categoryResponse);
                }
            }

            return ResponseEntity.ok(categoryResponses);

        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
