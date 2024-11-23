package jsb.ep4api.specifications;

import jsb.ep4api.entities.Genre;
import jsb.ep4api.entities.Movie;
import jsb.ep4api.entities.MovieGenre;
import org.hibernate.mapping.Join;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;

public class MovieSpecifications {
    public static Specification<Movie> hasId(Long id) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("id"), id);
    }

    public static Specification<Movie> hasIdNot(Long id) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.notEqual(root.get("id"), id);
    }

    public static Specification<Movie> randomOrder() {
        return (root, query, criteriaBuilder) -> {
            query.orderBy(criteriaBuilder.asc(criteriaBuilder.function("RAND", Double.class)));
            return query.getRestriction();
        };
    }

    public static Specification<Movie> orderByCreatedAtDesc() {
        return (root, query, criteriaBuilder) -> {
            query.orderBy(criteriaBuilder.desc(root.get("createdAt")));
            return query.getRestriction();
        };
    }

    public static Specification<Movie> orderByViews() {
        return (root, query, criteriaBuilder) -> {
            query.orderBy(criteriaBuilder.desc(root.get("views")));
            return query.getRestriction();
        };
    }

    public static Specification<Movie> hasIdIn(List<Long> ids) {
        return (root, query, criteriaBuilder) -> root.get("id").in(ids);
    }

    public static Specification<Movie> hasSlug(String slug) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("slug"), slug);
    }

    public static Specification<Movie> titleContains(String title) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.like(root.get("title"), "%" + title + "%");
    }

    public static Specification<Movie> originalTitleContains(String originalTitle) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.like(root.get("originalTitle"), "%" + originalTitle + "%");
    }

    public static Specification<Movie> storylineContains(String storyline) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.like(root.get("storyline"), "%" + storyline + "%");
    }

    public static Specification<Movie> releaseYearBetween(int minReleaseYear, int maxReleaseYear) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.between(root.get("releaseYear"), minReleaseYear, maxReleaseYear);
    }

    public static Specification<Movie> hasDurationBetween(int minDuration, int maxDuration) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.between(root.get("duration"), minDuration, maxDuration);
    }

    public static Specification<Movie> hasCountryId(Long countryId) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("country").get("id"), countryId);
    }


    public static Specification<Movie> hasPackageId(Long packageId) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("aPackage").get("id"), packageId);
    }

    public static Specification<Movie> canRent(boolean canRent) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("canRent"), canRent);
    }

    public static Specification<Movie> isFreeMovie() {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("price"), 0);
    }

    public static Specification<Movie> hasPriceBetween(double minPrice, double maxPrice) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.between(root.get("price"), minPrice, maxPrice);
    }

    public static Specification<Movie> belongsToStudio(Long studioId) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("studio").get("id"), studioId);
    }

    public static Specification<Movie> hasVideoQuality(Long videoModeId) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("videoMode").get("id"), videoModeId);
    }

    public static Specification<Movie> belongsToClassification(Long classificationId) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("classification").get("id"), classificationId);
    }

    public static Specification<Movie> belongsToCategory(Long categoryId) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("category").get("id"), categoryId);
    }

    public static Specification<Movie> hasShowFlag(boolean isShow) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("isShow"), isShow);
    }

    public static Specification<Movie> hasShowAtHomeFlag(boolean isShowAtHome) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("isShowAtHome"), isShowAtHome);
    }

    public static Specification<Movie> hasNoDeletedFlag() {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("deleteFlag"), false);
    }

    public static Specification<Movie> hasGenreId(Long[] genreIds) {
        return (root, query, criteriaBuilder) -> root.join("genres").get("id").in(genreIds);
    }

    public static Specification<Movie> hasLanguageId(Long[] languageIds) {
        return (root, query, criteriaBuilder) -> root.join("languages").get("id").in(languageIds);
    }

}
