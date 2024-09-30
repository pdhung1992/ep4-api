package jsb.ep4api.specifications;

import jsb.ep4api.entities.MovieGenre;
import org.springframework.data.jpa.domain.Specification;

public class MovieGenreSpecifications {
    public static Specification<MovieGenre> hasId(Long id) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("id"), id);
    }
    public static Specification<MovieGenre> hasMovieId(Long movieId) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("movie").get("id"), movieId);
    }

    public static Specification<MovieGenre> hasGenreId(Long genreId) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("genre").get("id"), genreId);
    }

    public static Specification<MovieGenre> hasNoDeletedFlag() {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("deleteFlag"), false);
    }

}
