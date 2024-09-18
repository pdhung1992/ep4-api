package jsb.ep4api.services;

import jsb.ep4api.entities.Classification;
import jsb.ep4api.repositories.ClassificationRepository;
import jsb.ep4api.specifications.ClassificationSpecifications;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

@Service
public class ClassificationService {
    @Autowired
    private ClassificationRepository classificationRepository;

    public Page<Classification> getAllClassifications(
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

        Specification<Classification> spec = Specification.where(null);
        if (name != null) {
            spec = spec.and(ClassificationSpecifications.hasName(name));
        }
        spec = spec.and(ClassificationSpecifications.hasNoDeleteFlag());

        return classificationRepository.findAll(spec, pageable);
    }
}
