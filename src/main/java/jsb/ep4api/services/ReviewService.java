package jsb.ep4api.services;

import jsb.ep4api.entities.Review;
import jsb.ep4api.repositories.ReviewRepository;
import jsb.ep4api.specifications.ReviewSpecifications;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ReviewService {
    @Autowired
    private ReviewRepository reviewRepository;

    public Page<Review> getReviewsByMovieId(
            int pageNo,
            int pageSize,
            Long movieId
    ) {
        var sort = Sort.by("createdAt").descending();
        Pageable pageable = PageRequest.of(pageNo - 1, pageSize, sort);

        Specification<Review> spec = Specification.where(null);
        spec = spec.and(ReviewSpecifications.hasMovieId(movieId));
        spec = spec.and((ReviewSpecifications.hasNoParentId()));
        spec = spec.and(ReviewSpecifications.hasNoDeletedFlag());

        return reviewRepository.findAll(spec, pageable);
    }

    public Page<Review> getReviewsByParentId(
            int pageNo,
            int pageSize,
            Long parentId
    ) {
        var sort = Sort.by("createdAt").descending();
        Pageable pageable = PageRequest.of(pageNo - 1, pageSize, sort);

        Specification<Review> spec = Specification.where(null);
        spec = spec.and(ReviewSpecifications.hasParentId(parentId));
        spec = spec.and(ReviewSpecifications.hasNoDeletedFlag());

        return reviewRepository.findAll(spec, pageable);
    }

    public Review getReviewById(Long reviewId){
        Specification<Review> spec = Specification.where(null);
        spec = spec.and(ReviewSpecifications.hasId(reviewId));
        spec = spec.and(ReviewSpecifications.hasNoDeletedFlag());

        return reviewRepository.findOne(spec).orElse(null);
    }

    public Long getReviewCountByMovieId(Long movieId){
        Specification<Review> spec = Specification.where(null);
        spec = spec.and(ReviewSpecifications.hasMovieId(movieId));
        spec = spec.and(ReviewSpecifications.hasNoDeletedFlag());

        return reviewRepository.count(spec);
    }

    public Long getReviewCountByParentId(Long parentId){
        Specification<Review> spec = Specification.where(null);
        spec = spec.and(ReviewSpecifications.hasParentId(parentId));
        spec = spec.and(ReviewSpecifications.hasNoDeletedFlag());

        return reviewRepository.count(spec);
    }

    public Double calculateRatingByMovieId(Long movieId){
        Specification<Review> spec = Specification.where(null);
        spec = spec.and(ReviewSpecifications.hasMovieId(movieId));
        spec = spec.and(ReviewSpecifications.hasNoParentId());
        spec = spec.and(ReviewSpecifications.hasNoDeletedFlag());

        return null;
    }

    public long countReviewsByTimeRange(LocalDateTime start, LocalDateTime end, Long movieId){
        Specification<Review> spec = Specification.where(null);
        spec = spec.and(ReviewSpecifications.hasCreatedBetween(start, end));
        spec = spec.and(ReviewSpecifications.hasNoDeletedFlag());
        if (movieId != null) {
            spec = spec.and(ReviewSpecifications.hasMovieId(movieId));
        }

        long count = 0;
        List<Review> reviews = reviewRepository.findAll(spec);
        for (Review review : reviews) {
            count++;
        }

        return count;
    }

    public void createReview(Review review){
        reviewRepository.save(review);
    }

    public void updateReview(Review review){
        reviewRepository.save(review);
    }
}
