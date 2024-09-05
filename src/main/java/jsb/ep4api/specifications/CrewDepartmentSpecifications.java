package jsb.ep4api.specifications;

import jsb.ep4api.entities.CrewDepartment;
import org.springframework.data.jpa.domain.Specification;

public class CrewDepartmentSpecifications {
    public static Specification<CrewDepartment> hasNoDeleteFlag() {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("deleteFlag"), false);
    }
}
