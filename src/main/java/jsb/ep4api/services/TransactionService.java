package jsb.ep4api.services;

import jsb.ep4api.entities.Movie;
import jsb.ep4api.entities.Package;
import jsb.ep4api.entities.Transaction;
import jsb.ep4api.entities.User;
import jsb.ep4api.payloads.requests.TransactionRequest;
import jsb.ep4api.repositories.TransactionRepository;
import jsb.ep4api.specifications.TransactionSpecifications;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

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

    public Transaction findByCode(String code) {
        Specification<Transaction> spec = Specification.where(null);
        spec = spec.and(TransactionSpecifications.hasCode(code));
        spec = spec.and(TransactionSpecifications.hasNoDeleteFlag());
        return transactionRepository.findOne(spec).orElse(null);
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
        transaction.setCreatedAt(CURRENT_TIME);
        transaction.setModifiedAt(CURRENT_TIME);

        transactionRepository.save(transaction);
    }

    public void updateTransactionStatus(String code, int status) {
        Transaction transaction = findByCode(code);
        if (transaction == null) {
            throw new RuntimeException("Transaction not found");
        }
        transaction.setStatus(status);
        transaction.setModifiedAt(CURRENT_TIME);
        transactionRepository.save(transaction);
    }

}
