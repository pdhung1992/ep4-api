package jsb.ep4api.controllers;


import jsb.ep4api.config.HasFunctionAccessToClass;
import jsb.ep4api.entities.MovieMonthlyReport;
import jsb.ep4api.entities.Transaction;
import jsb.ep4api.payloads.responses.SpecResponse;
import jsb.ep4api.payloads.responses.TransactionResponse;
import jsb.ep4api.services.MovieMonthlyReportService;
import jsb.ep4api.services.MovieService;
import jsb.ep4api.services.ReviewService;
import jsb.ep4api.services.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static jsb.ep4api.constants.Constants.REPORT_MANAGEMENT_FUNCTION;

@RestController
@RequestMapping("/api/reports")
@HasFunctionAccessToClass(REPORT_MANAGEMENT_FUNCTION)
public class ReportController {
    @Autowired
    TransactionService transactionService;

    @Autowired
    MovieMonthlyReportService movieMonthlyReportService;

    @Autowired
    ReviewService reviewService;

    @Autowired
    MovieService movieService;

    @GetMapping("/revenue/transactions")
    public ResponseEntity<?> getRevenueByMonth(
            @RequestParam(required = false) Integer pageNo,
            @RequestParam(required = false) Integer pageSize,
            @RequestParam(required = false) String sortField,
            @RequestParam(required = false) String sortDir,
            @RequestParam(required = false) String month,
            @RequestParam(required = false) String from
    ) {
        try {
            if (pageNo == null) {
                pageNo = 1;
            }
            if (pageSize == null) {
                pageSize = 10;
            }
            if (sortField == null) {
                sortField = "id";
            }
            if (sortDir == null) {
                sortDir = "asc";
            }

            LocalDateTime startOfMonth;
            LocalDateTime endOfMonth;
            //Time
            if (month != null && !month.trim().isEmpty()) {
                YearMonth yearMonth = YearMonth.parse(month, DateTimeFormatter.ofPattern("yyyy-MM"));
                startOfMonth = yearMonth.atDay(1).atStartOfDay();
                endOfMonth = yearMonth.atEndOfMonth().atTime(23, 59, 59);
            } else {
                YearMonth currentMonth = YearMonth.now();
                startOfMonth = currentMonth.atDay(1).atStartOfDay();
                endOfMonth = currentMonth.atEndOfMonth().atTime(23, 59, 59);
            }

            Page<Transaction> transactions = transactionService.getTransactionByTimeRange(pageNo, pageSize, sortField, sortDir, startOfMonth, endOfMonth, from);
            List<Transaction> transactionList = transactions.getContent();
            List<TransactionResponse> responses = new ArrayList<>();
            for (Transaction transaction : transactionList) {
                TransactionResponse response = new TransactionResponse();
                response.setId(transaction.getId());
                response.setCode(transaction.getCode());
                response.setAmount(transaction.getAmount());
                response.setGateway(transaction.getGateway());
                response.setStatus(transaction.getStatus());
                response.setUserId(transaction.getUser().getId());
                response.setIsPackage(transaction.getIsPackage());
                if (transaction.getAPackage() != null) {
                    response.setPackageId(transaction.getAPackage().getId());
                    response.setPackageName(transaction.getAPackage().getPackageName());
                } else {
                    response.setPackageId(null);
                    response.setPackageName("Unknown Package");
                }

                if (transaction.getMovie() != null) {
                    response.setMovieId(transaction.getMovie().getId());
                    response.setMovieTitle(transaction.getMovie().getTitle());
                } else {
                    response.setMovieId(null);
                    response.setMovieTitle("Unknown Movie");
                }
                response.setCreatedAt(transaction.getCreatedAt());

                responses.add(response);
            }

            int totalPages = transactions.getTotalPages();
            long totalItems = transactions.getTotalElements();
            long fromItem = (pageNo - 1) * pageSize + 1;
            long toItem = 0;
            if (totalItems > pageSize) {
                toItem = fromItem + pageSize - 1;
            } else {
                toItem = totalItems;
            }

            SpecResponse specResponse = new SpecResponse();
            specResponse.setCurrentPage(pageNo);
            specResponse.setTotalPages(totalPages);
            specResponse.setTotalItems(totalItems);
            specResponse.setFromItem(fromItem);
            specResponse.setToItem(toItem);
            specResponse.setPageSize(pageSize);
            specResponse.setSortField(sortField);
            specResponse.setSortDir(sortDir);
            specResponse.setData(responses);

            return ResponseEntity.ok(specResponse);

        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/revenue/statistics")
    public ResponseEntity<?> getRevenueStatistics(
            @RequestParam(required = false) String month
    ) {
        try {

            LocalDateTime startOfMonth;
            LocalDateTime endOfMonth;
            LocalDateTime startOfLastMonth;
            LocalDateTime endOfLastMonth;
            //Time
            if (month != null && !month.trim().isEmpty()) {
                YearMonth yearMonth = YearMonth.parse(month, DateTimeFormatter.ofPattern("yyyy-MM"));
                startOfMonth = yearMonth.atDay(1).atStartOfDay();
                endOfMonth = yearMonth.atEndOfMonth().atTime(23, 59, 59);
                startOfLastMonth = yearMonth.minusMonths(1).atDay(1).atStartOfDay();
                endOfLastMonth = yearMonth.minusMonths(1).atEndOfMonth().atTime(23, 59, 59);
            } else {
                YearMonth currentMonth = YearMonth.now();
                startOfMonth = currentMonth.atDay(1).atStartOfDay();
                endOfMonth = currentMonth.atEndOfMonth().atTime(23, 59, 59);
                startOfLastMonth = currentMonth.minusMonths(1).atDay(1).atStartOfDay();
                endOfLastMonth = currentMonth.minusMonths(1).atEndOfMonth().atTime(23, 59, 59);
            }

            double revenueThisMonth = transactionService.getTotalRevenueByTimeRange(startOfMonth, endOfMonth, null);
            double revenueLastMonth = transactionService.getTotalRevenueByTimeRange(startOfLastMonth, endOfLastMonth, null);
            double revenueDifference = 0.0;
            if (revenueLastMonth > 0) {
                revenueDifference = (revenueThisMonth - revenueLastMonth) / revenueLastMonth * 100;
                revenueDifference = Math.round(revenueDifference * 100.0) / 100.0;
            }

            double revenueFromMovieThisMonth = transactionService.getTotalRevenueByTimeRange(startOfMonth, endOfMonth, "movie");
            double revenueFromMovieLastMonth = transactionService.getTotalRevenueByTimeRange(startOfLastMonth, endOfLastMonth, "movie");
            double revenueFromMovieDifference = 0.0;
            if (revenueFromMovieLastMonth > 0) {
                revenueFromMovieDifference = (revenueFromMovieThisMonth - revenueFromMovieLastMonth) / revenueFromMovieLastMonth * 100;
                revenueFromMovieDifference = Math.round(revenueFromMovieDifference * 100.0) / 100.0;
            }

            double revenueFromPackageThisMonth = transactionService.getTotalRevenueByTimeRange(startOfMonth, endOfMonth, "package");
            double revenueFromPackageLastMonth = transactionService.getTotalRevenueByTimeRange(startOfLastMonth, endOfLastMonth, "package");
            double revenueFromPackageDifference = 0.0;
            if (revenueFromPackageLastMonth > 0) {
                revenueFromPackageDifference = (revenueFromPackageThisMonth - revenueFromPackageLastMonth) / revenueFromPackageLastMonth * 100;
                revenueFromPackageDifference = Math.round(revenueFromPackageDifference * 100.0) / 100.0;
            }

            long totalTransactionsThisMonth = transactionService.countTransactionsByTimeRange(startOfMonth, endOfMonth, null);
            long totalTransactionsLastMonth = transactionService.countTransactionsByTimeRange(startOfLastMonth, endOfLastMonth, null);
            double totalTransactionsDifference = 0.0;
            if (totalTransactionsLastMonth > 0) {
                totalTransactionsDifference = (double) (totalTransactionsThisMonth - totalTransactionsLastMonth) / totalTransactionsLastMonth * 100;
                totalTransactionsDifference = Math.round(totalTransactionsDifference * 100.0) / 100.0;
            }

            HashMap<String, Object> response = new HashMap<>();
            response.put("revenueThisMonth", revenueThisMonth);
            response.put("revenueLastMonth", revenueLastMonth);
            response.put("revenueDifference", revenueDifference);
            response.put("revenueFromMovieThisMonth", revenueFromMovieThisMonth);
            response.put("revenueFromMovieLastMonth", revenueFromMovieLastMonth);
            response.put("revenueFromMovieDifference", revenueFromMovieDifference);
            response.put("revenueFromPackageThisMonth", revenueFromPackageThisMonth);
            response.put("revenueFromPackageLastMonth", revenueFromPackageLastMonth);
            response.put("revenueFromPackageDifference", revenueFromPackageDifference);
            response.put("totalTransactionsThisMonth", totalTransactionsThisMonth);
            response.put("totalTransactionsLastMonth", totalTransactionsLastMonth);
            response.put("totalTransactionsDifference", totalTransactionsDifference);

            return ResponseEntity.ok(response);

        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/dashboard/statistics")
    public ResponseEntity<?> getDashboardStatistics(
            @RequestParam(required = false) String month
    ) {
        try {
            LocalDateTime startOfMonth;
            LocalDateTime endOfMonth;
            LocalDateTime startOfLastMonth;
            int lastMonth;
            int thisMonth;
            int year;
            LocalDateTime endOfLastMonth;
            //Time
            if (month != null && !month.trim().isEmpty()) {
                YearMonth yearMonth = YearMonth.parse(month, DateTimeFormatter.ofPattern("yyyy-MM"));
                startOfMonth = yearMonth.atDay(1).atStartOfDay();
                endOfMonth = yearMonth.atEndOfMonth().atTime(23, 59, 59);
                startOfLastMonth = yearMonth.minusMonths(1).atDay(1).atStartOfDay();
                endOfLastMonth = yearMonth.minusMonths(1).atEndOfMonth().atTime(23, 59, 59);
                lastMonth = yearMonth.minusMonths(1).getMonthValue();
                thisMonth = yearMonth.getMonthValue();
                year = yearMonth.getYear();
            } else {
                YearMonth currentMonth = YearMonth.now();
                startOfMonth = currentMonth.atDay(1).atStartOfDay();
                endOfMonth = currentMonth.atEndOfMonth().atTime(23, 59, 59);
                startOfLastMonth = currentMonth.minusMonths(1).atDay(1).atStartOfDay();
                endOfLastMonth = currentMonth.minusMonths(1).atEndOfMonth().atTime(23, 59, 59);
                lastMonth = currentMonth.minusMonths(1).getMonthValue();
                thisMonth = currentMonth.getMonthValue();
                year = currentMonth.getYear();
            }

            // Revenue and Transactions
            double revenueThisMonth = transactionService.getTotalRevenueByTimeRange(startOfMonth, endOfMonth, null);
            double revenueLastMonth = transactionService.getTotalRevenueByTimeRange(startOfLastMonth, endOfLastMonth, null);
            double revenueDifference = 0.0;
            if (revenueLastMonth > 0) {
                revenueDifference = (revenueThisMonth - revenueLastMonth) / revenueLastMonth * 100;
                revenueDifference = Math.round(revenueDifference * 100.0) / 100.0;
            }

            long totalTransactionsThisMonth = transactionService.countTransactionsByTimeRange(startOfMonth, endOfMonth, null);
            long totalTransactionsLastMonth = transactionService.countTransactionsByTimeRange(startOfLastMonth, endOfLastMonth, null);
            double totalTransactionsDifference = 0.0;
            if (totalTransactionsLastMonth > 0) {
                totalTransactionsDifference = (double) (totalTransactionsThisMonth - totalTransactionsLastMonth) / totalTransactionsLastMonth * 100;
                totalTransactionsDifference = Math.round(totalTransactionsDifference * 100.0) / 100.0;
            }

            // Views and reviews
            long lastMonthViews = movieMonthlyReportService.totalViewOfAllMoviesInMonth(lastMonth, year);
            long totalViewOfAllMovies = movieService.totalViewOfAllMovies();
            long currentMonthViews = totalViewOfAllMovies - lastMonthViews;
            double viewsDifference = 0.0;
            if (lastMonthViews > 0) {
                viewsDifference = (double) (currentMonthViews - lastMonthViews) / lastMonthViews * 100;
                viewsDifference = Math.round(viewsDifference * 100.0) / 100.0;
            }

            long lastMonthReviews = reviewService.countReviewsByTimeRange(startOfLastMonth, endOfLastMonth, null);
            long currentMonthReviews = reviewService.countReviewsByTimeRange(startOfMonth, endOfMonth, null);
            double reviewsDifference = 0.0;
            if (lastMonthReviews > 0) {
                reviewsDifference = (double) (currentMonthReviews - lastMonthReviews) / lastMonthReviews * 100;
                reviewsDifference = Math.round(reviewsDifference * 100.0) / 100.0;
            }


            HashMap<String, Object> response = new HashMap<>();
            response.put("revenueThisMonth", revenueThisMonth);
            response.put("revenueDifference", revenueDifference);
            response.put("totalTransactionsThisMonth", totalTransactionsThisMonth);
            response.put("totalTransactionsDifference", totalTransactionsDifference);
            response.put("currentMonthViews", currentMonthViews);
            response.put("viewsDifference", viewsDifference);
            response.put("currentMonthReviews", currentMonthReviews);
            response.put("reviewsDifference", reviewsDifference);

            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
