package jsb.ep4api.specifications;


import jsb.ep4api.entities.MovieMonthlyReport;
import org.springframework.data.jpa.domain.Specification;

public class MovieMonthlyReportSpecifications {
    public static Specification<MovieMonthlyReport> hasMovieId(Long movieId) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("movie").get("id"), movieId);
    }

    public static Specification<MovieMonthlyReport> hasMonth(Integer month) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("month"), month);
    }

    public static Specification<MovieMonthlyReport> hasYear(Integer year) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("year"), year);
    }

    public static Specification<MovieMonthlyReport> hasNoDeletedFlag() {
        return (root, query, criteriaBuilder) -> criteriaBuilder.isFalse(root.get("deleteFlag"));
    }
}
