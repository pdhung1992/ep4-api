package jsb.ep4api.specifications;

import org.springframework.data.jpa.domain.Specification;
import jsb.ep4api.entities.Package;

public class PackageSpecifications {

    public static Specification<Package> hasId(Long id) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("id"), id);
    }
    public static Specification<Package> hasName(String name) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("packageName"), name);
    }

    public static Specification<Package> hasNameLike(String name) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.like(root.get("packageName"), "%" + name + "%");
    }

    public static Specification<Package> hasPrice(Double price) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("price"), price);
    }

    public static Specification<Package> isNotFreePackage() {
        return (root, query, criteriaBuilder) -> criteriaBuilder.notEqual(root.get("price"), 0);
    }

    public static Specification<Package> isFreePackage() {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("price"), 0);
    }

    public static Specification<Package> hasPriceBetween(Double minPrice, Double maxPrice) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.between(root.get("price"), minPrice, maxPrice);
    }

    public static Specification<Package> hasNoDeleteFlag() {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("deleteFlag"), false);
    }

    public static Specification<Package> hasSlug(String slug) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("slug"), slug);
    }
}
