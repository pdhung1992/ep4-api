package jsb.ep4api.specifications;

import jsb.ep4api.entities.Transaction;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDateTime;

import static jsb.ep4api.constants.Constants.*;

public class TransactionSpecifications {
    public static Specification<Transaction> hasCode(String code) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("code"), code);
    }

    public static Specification<Transaction> hasNoDeleteFlag() {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("deleteFlag"), DEFAULT_DELETE_FLAG);
    }

    public static Specification<Transaction> hasUserId(Long userId) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("user").get("id"), userId);
    }

    public static Specification<Transaction> hasTimeRange(LocalDateTime startTime, LocalDateTime endTime) {
        return (root, query, criteriaBuilder) -> {
            if (startTime != null && endTime != null) {
                return criteriaBuilder.between(root.get("createdAt"), startTime, endTime);
            }
            return null;
        };
    }

    public static Specification<Transaction> hasFromPackage() {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("isPackage"), true);
    }

    public static Specification<Transaction> hasFromMovie() {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("isPackage"), false);
    }

    public static Specification<Transaction> isSuccessTransaction() {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("status"), PAYMENT_STATUS_SUCCESS);
    }
}
