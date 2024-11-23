package jsb.ep4api.crons;

import jsb.ep4api.entities.Movie;
import jsb.ep4api.entities.MovieMonthlyReport;
import jsb.ep4api.services.MovieMonthlyReportService;
import jsb.ep4api.services.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.YearMonth;
import java.util.List;

import static jsb.ep4api.constants.Constants.DEFAULT_DELETE_FLAG;

@Service
public class MovieReportCron {
    @Autowired
    MovieService movieService;

    @Autowired
    MovieMonthlyReportService movieMonthlyReportService;

    @Scheduled(cron = "0 0 0 1 * ?")
    public void generateMonthlyReport() {
        List<Movie> movies = movieService.getAllMovies();
        YearMonth lastMonth = YearMonth.now().minusMonths(1);
        int month = lastMonth.getMonthValue();
        int year = lastMonth.getYear();

        for (Movie movie : movies) {
            MovieMonthlyReport report = new MovieMonthlyReport();
            report.setMovie(movie);
            report.setMonth(month);
            report.setYear(year);
            report.setViews(movie.getViews());
            report.setDeleteFlag(DEFAULT_DELETE_FLAG);
            report.setCreatedAt(LocalDateTime.now());
            report.setModifiedAt(LocalDateTime.now());
            movieMonthlyReportService.saveMovieMonthlyReport(report);
        }
    }
}
