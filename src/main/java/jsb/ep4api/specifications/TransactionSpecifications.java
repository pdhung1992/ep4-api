package jsb.ep4api.specifications;

import jsb.ep4api.entities.Transaction;
import org.springframework.data.jpa.domain.Specification;

import static jsb.ep4api.constants.Constants.*;

public class TransactionSpecifications {
    public static Specification<Transaction> hasCode(String code) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("code"), code);
    }

    public static Specification<Transaction> hasNoDeleteFlag() {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("deleteFlag"), DEFAULT_DELETE_FLAG);
    }
}
