package jsb.ep4api.services;

import jsb.ep4api.entities.UserPackage;
import jsb.ep4api.repositories.UserPackageRepository;
import jsb.ep4api.specifications.UserPackageSpecifications;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

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
