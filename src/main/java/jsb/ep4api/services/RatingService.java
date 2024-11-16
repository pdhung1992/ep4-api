package jsb.ep4api.services;

import jsb.ep4api.entities.Rating;
import jsb.ep4api.repositories.RatingRepository;
import jsb.ep4api.specifications.RatingSpecifications;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RatingService {
    @Autowired
    private RatingRepository ratingRepository;

    public double getAverageRatingByMovieId(Long movieId){
        Specification<Rating> spec = Specification.where(null);
        spec = spec.and(RatingSpecifications.hasMovieId(movieId));
        spec = spec.and(RatingSpecifications.hasNoDeleteFlag());

        List<Rating> ratings = ratingRepository.findAll(spec);
        if (ratings.isEmpty()) return 0;

        double average = ratings.stream()
                .mapToDouble(Rating::getValue)
                .average()
                .orElse(0.0);

        return Math.round(average * 10.0) / 10.0;
    }

    public Long getRatingCountByMovieId(Long movieId){
        Specification<Rating> spec = Specification.where(null);
        spec = spec.and(RatingSpecifications.hasMovieId(movieId));
        spec = spec.and(RatingSpecifications.hasNoDeleteFlag());

        return ratingRepository.count(spec);
    }

    public Rating getRatingByUserIdAndMovieId(Long userId, Long movieId){
        Specification<Rating> spec = Specification.where(null);
        spec = spec.and(RatingSpecifications.hasUserId(userId));
        spec = spec.and(RatingSpecifications.hasMovieId(movieId));
        spec = spec.and(RatingSpecifications.hasNoDeleteFlag());

        return ratingRepository.findOne(spec).orElse(null);
    }

    public int getRatingValueByUserIdAndMovieId(Long userId, Long movieId){
        Specification<Rating> spec = Specification.where(null);
        spec = spec.and(RatingSpecifications.hasUserId(userId));
        spec = spec.and(RatingSpecifications.hasMovieId(movieId));
        spec = spec.and(RatingSpecifications.hasNoDeleteFlag());

        Rating rating = ratingRepository.findOne(spec).orElse(null);
        if (rating == null){
            return 0;
        } else {
            return rating.getValue();
        }
    }

    public void saveRating(Rating rating){
        ratingRepository.save(rating);
    }
}
