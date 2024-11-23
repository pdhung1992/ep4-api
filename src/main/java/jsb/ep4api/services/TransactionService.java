package jsb.ep4api.services;

import jsb.ep4api.entities.Movie;
import jsb.ep4api.entities.Package;
import jsb.ep4api.entities.Transaction;
import jsb.ep4api.entities.User;
import jsb.ep4api.payloads.requests.TransactionRequest;
import jsb.ep4api.repositories.TransactionRepository;
import jsb.ep4api.specifications.TransactionSpecifications;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

import static jsb.ep4api.constants.Constants.*;

@Service
public class TransactionService {
    @Autowired
    private TransactionRepository transactionRepository;
    @Autowired
    private UserService userService;
    @Autowired
    private PackageService packageService;
    @Autowired
    private MovieService movieService;

    public Page<Transaction> getTransactionsByUser(Long userId, Integer pageNo, Integer pageSize, String sortField, String sortDir) {
        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ?
                Sort.by(sortField).ascending() :
                Sort.by(sortField).descending();
        Pageable pageable = PageRequest.of(pageNo - 1, pageSize, sort);

        Specification<Transaction> spec = Specification.where(null);
        spec = spec.and(TransactionSpecifications.hasUserId(userId));
        spec = spec.and(TransactionSpecifications.hasNoDeleteFlag());

        return transactionRepository.findAll(spec, pageable);
    }

    public Transaction findByCode(String code) {
        Specification<Transaction> spec = Specification.where(null);
        spec = spec.and(TransactionSpecifications.hasCode(code));
        spec = spec.and(TransactionSpecifications.hasNoDeleteFlag());
        return transactionRepository.findOne(spec).orElse(null);
    }

    public Page<Transaction> getTransactionByTimeRange(Integer pageNo, Integer pageSize, String sortField, String sortDir, LocalDateTime startTime, LocalDateTime endTime, String from) {
        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ?
                Sort.by(sortField).ascending() :
                Sort.by(sortField).descending();
        Pageable pageable = PageRequest.of(pageNo - 1, pageSize, sort);

        Specification<Transaction> spec = Specification.where(null);
        spec = spec.and(TransactionSpecifications.hasTimeRange(startTime, endTime));
        if (from != null) {
            if (from.equals("package")) {
                spec = spec.and(TransactionSpecifications.hasFromPackage());
            } else if (from.equals("movie")) {
                spec = spec.and(TransactionSpecifications.hasFromMovie());
            }
        }
        spec = spec.and(TransactionSpecifications.hasNoDeleteFlag());

        return transactionRepository.findAll(spec, pageable);
    }

    public Double getTotalRevenueByTimeRange(LocalDateTime startTime, LocalDateTime endTime, String from) {
        Specification<Transaction> spec = Specification.where(null);
        spec = spec.and(TransactionSpecifications.hasTimeRange(startTime, endTime));
        spec = spec.and(TransactionSpecifications.isSuccessTransaction());
        spec = spec.and(TransactionSpecifications.hasNoDeleteFlag());

        if (from != null) {
            if (from.equals("package")) {
                spec = spec.and(TransactionSpecifications.hasFromPackage());
            } else if (from.equals("movie")) {
                spec = spec.and(TransactionSpecifications.hasFromMovie());
            }
        }

        List<Transaction> transactions = transactionRepository.findAll(spec);
        double total = 0;
        for (Transaction transaction : transactions) {
            total += transaction.getAmount();
        }
        return total;
    }

    public long countTransactionsByTimeRange(LocalDateTime startTime, LocalDateTime endTime, String from) {
        Specification<Transaction> spec = Specification.where(null);
        spec = spec.and(TransactionSpecifications.hasTimeRange(startTime, endTime));
        spec = spec.and(TransactionSpecifications.isSuccessTransaction());
        spec = spec.and(TransactionSpecifications.hasNoDeleteFlag());

        if (from != null) {
            if (from.equals("package")) {
                spec = spec.and(TransactionSpecifications.hasFromPackage());
            } else if (from.equals("movie")) {
                spec = spec.and(TransactionSpecifications.hasFromMovie());
            }
        }

        return transactionRepository.count(spec);
    }

    public void createTransaction(TransactionRequest request) {
        Transaction transaction = new Transaction();
        transaction.setCode(request.getCode());
        transaction.setAmount(request.getAmount());
        transaction.setContent(request.getContent());
        transaction.setGateway(request.getGateway());
        transaction.setStatus(PAYMENT_STATUS_PENDING);

        Long userId = request.getUserId();
        User user = userService.findById(userId);
        if (user == null) {
            throw new RuntimeException("User not found");
        }
        transaction.setUser(user);

        if (request.getIsPackage()) {
            Long packageId = request.getPackageId();
            Package aPackage = packageService.getPackageById(packageId);
            if (aPackage == null) {
                throw new RuntimeException("Package not found");
            }
            transaction.setIsPackage(true);
            transaction.setAPackage(aPackage);
        } else {
            Long movieId = request.getMovieId();
            Movie movie = movieService.getMovieById(movieId);
            if (movie == null) {
                throw new RuntimeException("Movie not found");
            }
            transaction.setIsPackage(false);
            transaction.setMovie(movie);
        }

        transaction.setDeleteFlag(DEFAULT_DELETE_FLAG);
        transaction.setCreatedAt(LocalDateTime.now());
        transaction.setModifiedAt(LocalDateTime.now());

        transactionRepository.save(transaction);
    }

    public void updateTransactionStatus(String code, int status) {
        Transaction transaction = findByCode(code);
        if (transaction == null) {
            throw new RuntimeException("Transaction not found");
        }
        transaction.setStatus(status);
        transaction.setModifiedAt(LocalDateTime.now());
        transactionRepository.save(transaction);
    }

}
