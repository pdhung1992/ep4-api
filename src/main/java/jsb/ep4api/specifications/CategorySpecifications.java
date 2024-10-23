package jsb.ep4api.specifications;


import jsb.ep4api.entities.Category;
import org.springframework.data.jpa.domain.Specification;

public class CategorySpecifications {

    public static Specification<Category> hasId(Long id) {
        return (root, query, builder) -> builder.equal(root.get("id"), id);
    }
    public static Specification<Category> hasNameContains(String keyword) {
        return (root, query, builder) -> builder.like(root.get("name"), "%" + keyword + "%");
    }

    public static Specification<Category> haveNoDeleteFlag() {
        return (root, query, builder) -> builder.equal(root.get("deleteFlag"), false);
    }
}
