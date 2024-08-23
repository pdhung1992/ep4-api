package jsb.ep4api.services;

import jsb.ep4api.entities.Function;
import jsb.ep4api.repositories.FunctionRepository;
import jsb.ep4api.specifications.FunctionSpecifications;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FunctionService {
    @Autowired
    private FunctionRepository functionRepository;

    public Function findFunctionById(Long id) {
        return functionRepository.findById(id).orElse(null);
    }

    public List<Function> findAllParentFunctions() {
        Specification<Function> spec = Specification.where(null);
        spec = spec.and(FunctionSpecifications.hasNoDeleteFlag());
        spec = spec.and(FunctionSpecifications.hasNoParentId());
        spec = spec.and(FunctionSpecifications.hasSortOrderAsc());
        return functionRepository.findAll(spec);
    }

    public List<Function> findAllFunctionsByParentId(Long parentId) {
        Specification<Function> spec = Specification.where(null);
        spec = spec.and(FunctionSpecifications.hasNoDeleteFlag());
        spec = spec.and(FunctionSpecifications.hasParentId(parentId));
        spec = spec.and(FunctionSpecifications.hasSortOrderAsc());
        return functionRepository.findAll(spec);
    }

    public List<Function> findShowFunctionsByParentId(Long parentId) {
        Specification<Function> spec = Specification.where(null);
        spec = spec.and(FunctionSpecifications.hasNoDeleteFlag());
        spec = spec.and(FunctionSpecifications.hasShowOnMenu());
        spec = spec.and(FunctionSpecifications.hasParentId(parentId));
        spec = spec.and(FunctionSpecifications.hasSortOrderAsc());
        return functionRepository.findAll(spec);
    }

}
