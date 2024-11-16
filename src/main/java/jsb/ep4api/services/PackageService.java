package jsb.ep4api.services;

import jsb.ep4api.entities.Package;
import jsb.ep4api.repositories.PackageRepository;
import jsb.ep4api.specifications.PackageSpecifications;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PackageService {
    @Autowired
    private PackageRepository packageRepository;

    public Page<Package> getPackages(
            int pageNo,
            int pageSize,
            String sortField,
            String sortDir,
            String name
    ){
        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ?
                Sort.by(sortField).ascending() :
                Sort.by(sortField).descending();
        Pageable pageable = PageRequest.of(pageNo - 1, pageSize, sort);

        Specification<Package> spec = Specification.where(null);
        if (name != null){
            spec = spec.and(PackageSpecifications.hasNameLike(name));
        }
        spec = spec.and(PackageSpecifications.hasNoDeleteFlag());

        return packageRepository.findAll(spec, pageable);
    }

    public List<Package> getPackagesSelect(){
        Specification<Package> spec = Specification.where(null);
        spec = spec.and(PackageSpecifications.hasNoDeleteFlag());

        return packageRepository.findAll(spec);
    }

    public List<Package> getNotFreePackages(){
        Specification<Package> spec = Specification.where(null);
        spec = spec.and(PackageSpecifications.isNotFreePackage());
        spec = spec.and(PackageSpecifications.hasNoDeleteFlag());

        return packageRepository.findAll(spec);
    }

    public Package getPackageById(Long id){
        Specification<Package> spec = Specification.where(null);
        spec = spec.and(PackageSpecifications.hasId(id));
        spec = spec.and(PackageSpecifications.hasNoDeleteFlag());

        return packageRepository.findOne(spec).orElse(null);
    }

    public boolean checkPackageIsFree(Long id){
        Specification<Package> spec = Specification.where(null);
        spec = spec.and(PackageSpecifications.hasId(id));
        spec = spec.and(PackageSpecifications.isFreePackage());
        spec = spec.and(PackageSpecifications.hasNoDeleteFlag());

        return packageRepository.findOne(spec).isPresent();
    }

    public void createPackage(Package pkg){
        packageRepository.save(pkg);
    }

    public void updatePackage(Package pkg){
        packageRepository.save(pkg);
    }

    public void deletePackage(Package pkg){
        pkg.setDeleteFlag(true);
        packageRepository.save(pkg);
    }
}
