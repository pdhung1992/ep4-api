package jsb.ep4api.services;

import jsb.ep4api.entities.Package;
import jsb.ep4api.entities.Transaction;
import jsb.ep4api.entities.User;
import jsb.ep4api.entities.UserPackage;
import jsb.ep4api.repositories.UserPackageRepository;
import jsb.ep4api.specifications.UserMovieSpecification;
import jsb.ep4api.specifications.UserPackageSpecifications;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

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

    public boolean checkUserCanWatchPackage(Long userId, Long moviePackageId) {
        List<Long> packagesInclude = getAllPackagesUserCanWatch(userId);
        return packagesInclude.contains(moviePackageId);
    }

    public List<Long> getAllPackagesUserCanWatch(Long userId) {
        Specification<UserPackage> spec = Specification.where(null);
        spec = spec.and(UserPackageSpecifications.hasUserId(userId));
        spec = spec.and(UserPackageSpecifications.hasNoDeletedFlag());
        spec = spec.and(UserPackageSpecifications.hasNotExpired());
        List<UserPackage> userPackages = userPackageRepository.findAll(spec);
        List<Package> packages = userPackages.stream().map(UserPackage::getAPackage).toList();
        List<Long> packageIds = new ArrayList<>();
        for (Package aPackage : packages) {
            String packagesInclude = aPackage.getPackagesInclude();
            if (packagesInclude != null) {
                String[] items = packagesInclude.replace("[","").replace("]", "").split(",");
                for (String item : items) {
                    packageIds.add(Long.parseLong(item.trim()));
                }
            }
        }

        packageIds = new ArrayList<>(new HashSet<>(packageIds));
        return packageIds;
    }

    public UserPackage getUserPackageByUserIdAndPackageId(Long userId, Long packageId) {
        Specification<UserPackage> spec = Specification.where(null);
        spec = spec.and(UserPackageSpecifications.hasUserId(userId));
        spec = spec.and(UserPackageSpecifications.hasPackageId(packageId));
        spec = spec.and(UserPackageSpecifications.hasNoDeletedFlag());
        return userPackageRepository.findOne(spec).orElse(null);
    }

    public List<Package> getPackagesByUserId(Long userId) {
        Specification<UserPackage> spec = Specification.where(null);
        spec = spec.and(UserPackageSpecifications.hasUserId(userId));
        spec = spec.and(UserPackageSpecifications.hasNoDeletedFlag());
        spec = spec.and(UserPackageSpecifications.hasNotExpired());
        List<UserPackage> userPackages = userPackageRepository.findAll(spec);
        return userPackages.stream().map(UserPackage::getAPackage).toList();
    }

    public void updateUserPackageByTransaction(Transaction transaction, User user) {
        Package aPackage = transaction.getAPackage();

        UserPackage userPackage = getUserPackageByUserIdAndPackageId(user.getId(), aPackage.getId());

        LocalDateTime current = LocalDateTime.now();

        if (userPackage != null) {
            if (userPackage.getExpiredAt().isAfter(current) && userPackage.getExpiredAt().isBefore(current.plusDays(aPackage.getExpirationUnit() - 1 ))) {
                userPackage.setExpiredAt(userPackage.getExpiredAt().plusDays(aPackage.getExpirationUnit()));
            } else if (userPackage.getExpiredAt().isBefore(current)) {
                userPackage.setExpiredAt(current.plusDays(aPackage.getExpirationUnit()));
            }
            userPackage.setModifiedAt(current);
            updateUserPackage(userPackage);
        } else {
            userPackage = new UserPackage();
            userPackage.setUser(user);
            userPackage.setAPackage(aPackage);
            userPackage.setExpiredAt(current.plusDays(aPackage.getExpirationUnit()));
            userPackage.setDeleteFlag(DEFAULT_DELETE_FLAG);
            userPackage.setCreatedAt(current);
            userPackage.setModifiedAt(current);
            createUserPackage(userPackage);
        }
    }

    public Page<UserPackage> getUserPackagesByUserId(Long userId, Boolean isExpired, Integer pageNo, Integer pageSize, String sortField, String sortDir) {
        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ?
                Sort.by(sortField).ascending() :
                Sort.by(sortField).descending();
        Pageable pageable = PageRequest.of(pageNo - 1, pageSize, sort);

        Specification<UserPackage> spec = Specification.where(null);
        spec = spec.and(UserPackageSpecifications.hasUserId(userId));
        spec = spec.and(UserPackageSpecifications.hasNoDeletedFlag());

        if (isExpired != null) {
            if (isExpired) {
                spec = spec.and(UserPackageSpecifications.hasExpired());
            } else {
                spec = spec.and(UserPackageSpecifications.hasNotExpired());
            }
        }

        return userPackageRepository.findAll(spec, pageable);
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
