package jsb.ep4api.specifications;

import jsb.ep4api.entities.MovieLanguage;
import org.springframework.data.jpa.domain.Specification;

public class MovieLanguageSpecifications {
    public static Specification<MovieLanguage> hasId(Long id) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("id"), id);
    }
    public static Specification<MovieLanguage> nameContains(String name) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.like(root.get("name"), "%" + name + "%");
    }

    public static Specification<MovieLanguage> hasLanguageId(Long languageId) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("language").get("id"), languageId);
    }

    public static Specification<MovieLanguage> hasMovieId(Long movieId) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("movie").get("id"), movieId);
    }

    public static Specification<MovieLanguage> hasNoDeletedFlag() {
        return (root, query, criteriaBuilder) -> criteriaBuilder.isFalse(root.get("deleted"));
    }
}
