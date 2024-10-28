package jsb.ep4api.services;

import jsb.ep4api.entities.Package;
import jsb.ep4api.entities.Transaction;
import jsb.ep4api.entities.User;
import jsb.ep4api.entities.UserPackage;
import jsb.ep4api.repositories.UserPackageRepository;
import jsb.ep4api.specifications.UserPackageSpecifications;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import static jsb.ep4api.constants.Constants.*;

@Service
public class UserPackageService {
    @Autowired
    private UserPackageRepository userPackageRepository;

    public boolean checkUserHasPackage(Long userId, Long packageId) {
        Specification<UserPackage> spec = Specification.where(null);
        spec = spec.and(UserPackageSpecifications.hasUserId(userId));
        spec = spec.and(UserPackageSpecifications.hasPackageId(packageId));
        spec = spec.and(UserPackageSpecifications.hasNoDeletedFlag());
        spec = spec.and(UserPackageSpecifications.hasNotExpired());

        return userPackageRepository.findOne(spec).isPresent();
    }

    public UserPackage getUserPackageByUserIdAndPackageId(Long userId, Long packageId) {
        Specification<UserPackage> spec = Specification.where(null);
        spec = spec.and(UserPackageSpecifications.hasUserId(userId));
        spec = spec.and(UserPackageSpecifications.hasPackageId(packageId));
        spec = spec.and(UserPackageSpecifications.hasNoDeletedFlag());
        return userPackageRepository.findOne(spec).orElse(null);
    }

    public void updateUserPackageByTransaction(Transaction transaction, User user) {
        Package aPackage = transaction.getAPackage();

        UserPackage userPackage = getUserPackageByUserIdAndPackageId(user.getId(), aPackage.getId());
        if (userPackage != null) {
            if (userPackage.getExpiredAt().isAfter(CURRENT_TIME)) {
                userPackage.setExpiredAt(userPackage.getExpiredAt().plusDays(aPackage.getExpirationUnit()));
                updateUserPackage(userPackage);
            } else {
                userPackage.setExpiredAt(CURRENT_TIME.plusDays(aPackage.getExpirationUnit()));
                updateUserPackage(userPackage);
            }
        } else {
            userPackage = new UserPackage();
            userPackage.setUser(user);
            userPackage.setAPackage(aPackage);
            userPackage.setExpiredAt(CURRENT_TIME.plusDays(aPackage.getExpirationUnit()));
            createUserPackage(userPackage);
        }
    }

    public void createUserPackage(UserPackage userPackage) {
        userPackageRepository.save(userPackage);
    }

    public void updateUserPackage(UserPackage userPackage) {
        userPackageRepository.save(userPackage);
    }

    public void deleteUserPackage(Long id) {
        UserPackage userPackage = userPackageRepository.findById(id).orElse(null);
        if (userPackage != null) {
            userPackage.setDeleteFlag(true);
            userPackageRepository.save(userPackage);
        }
    }
}
