package jsb.ep4api.specifications;

import jsb.ep4api.entities.MovieFile;
import org.springframework.data.jpa.domain.Specification;

public class MovieFileSpecifications {

    public static Specification<MovieFile> hasId(Long id) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("id"), id);
    }
    public static Specification<MovieFile> hasMovieId(Long movieId) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("movie").get("id"), movieId);
    }

    public static Specification<MovieFile> hasFilename(String fileName) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("fileName"), fileName);
    }

    public static Specification<MovieFile> hasNoDeleteFlag() {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("deleteFlag"), false);
    }

}
