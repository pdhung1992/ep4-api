package jsb.ep4api.services;

import jsb.ep4api.entities.Category;
import jsb.ep4api.repositories.CategoryRepository;
import jsb.ep4api.specifications.CategorySpecifications;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    public Page<Category> getAllCategories(
            int pageNo,
            int pageSize,
            String sortField,
            String sortDir,
            String name
    ) {
        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ?
                Sort.by(sortField).ascending() :
                Sort.by(sortField).descending();
        Pageable pageable = PageRequest.of(pageNo - 1, pageSize, sort);

        Specification<Category> spec = Specification.where(null);
        if (name != null) {
            spec = spec.and(CategorySpecifications.hasNameContains(name));
        }
        spec = spec.and(CategorySpecifications.haveNoDeleteFlag());

        return categoryRepository.findAll(spec, pageable);
    }

    public List<Category> getAllCategoriesSelect() {
        Specification<Category> spec = Specification.where(null);
        spec = spec.and(CategorySpecifications.haveNoDeleteFlag());

        return categoryRepository.findAll(spec);
    }

    public Category getCategoryById(Long id) {
        Specification<Category> spec = Specification.where(null);
        spec = spec.and(CategorySpecifications.hasId(id));
        spec = spec.and(CategorySpecifications.haveNoDeleteFlag());

        return categoryRepository.findOne(spec).orElse(null);
    }
}
