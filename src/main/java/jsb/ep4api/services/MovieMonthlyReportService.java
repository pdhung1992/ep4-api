package jsb.ep4api.services;

import jsb.ep4api.entities.MovieMonthlyReport;
import jsb.ep4api.repositories.MovieMonthlyReportRepository;
import jsb.ep4api.specifications.MovieMonthlyReportSpecifications;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MovieMonthlyReportService {
    @Autowired
    MovieMonthlyReportRepository movieMonthlyReportRepository;

    public MovieMonthlyReport getMovieMonthlyReport(Long movieId, Integer month, Integer year) {
        Specification<MovieMonthlyReport> spec = Specification.where(null);
        spec = spec.and(MovieMonthlyReportSpecifications.hasMovieId(movieId));
        spec = spec.and(MovieMonthlyReportSpecifications.hasMonth(month));
        spec = spec.and(MovieMonthlyReportSpecifications.hasYear(year));
        spec = spec.and(MovieMonthlyReportSpecifications.hasNoDeletedFlag());

        return movieMonthlyReportRepository.findOne(spec).orElse(null);
    }

    public long totalViewOfAllMoviesInMonth(Integer month, Integer year) {
        Specification<MovieMonthlyReport> spec = Specification.where(null);
        spec = spec.and(MovieMonthlyReportSpecifications.hasMonth(month));
        spec = spec.and(MovieMonthlyReportSpecifications.hasYear(year));
        spec = spec.and(MovieMonthlyReportSpecifications.hasNoDeletedFlag());

        long totalView = 0;
        List<MovieMonthlyReport> reports = movieMonthlyReportRepository.findAll(spec);
        for (MovieMonthlyReport report : reports) {
            totalView += report.getViews();
        }

        return totalView;
    }

    public void saveMovieMonthlyReport(MovieMonthlyReport report) {
        movieMonthlyReportRepository.save(report);
    }
}
