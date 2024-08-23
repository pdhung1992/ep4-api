package jsb.ep4api.services;

import jsb.ep4api.entities.Admin;
import jsb.ep4api.repositories.AdminRepository;
import jsb.ep4api.specifications.AdminSpecifications;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

@Service
public class AdminService {
    @Autowired
    private AdminRepository adminRepository;

    public Page<Admin> getAllAdmins(
            int pageNo,
            int pageSize,
            String sortField,
            String sortDir,
            String username,
            String fullName,
            String email,
            Long roleId
    ){
        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ?
                Sort.by(sortField).ascending() :
                Sort.by(sortField).descending();
        Pageable pageable = PageRequest.of(pageNo - 1, pageSize, sort);

        Specification<Admin> spec = Specification.where(null);
        if(username != null){
            spec = spec.and(AdminSpecifications.hasUsername(username));
        }
        if(fullName != null){
            spec = spec.and(AdminSpecifications.hasFullName(fullName));
        }
        if(email != null){
            spec = spec.and(AdminSpecifications.hasEmail(email));
        }
        if(roleId != null){
            spec = spec.and(AdminSpecifications.hasRoleId(roleId));
        }

        spec = spec.and(AdminSpecifications.hasNotDeleteFlag());

        return adminRepository.findAll(spec, pageable);
    };

    public Admin getAdminById(Long id){
        return adminRepository.findById(id).orElse(null);
    }

    public Boolean checkExistUsername(String username){
        return adminRepository.existsByUsername(username);
    }

    public boolean checkExistEmail(String email){
        return adminRepository.existsByEmail(email);
    }

    public void createAdmin(Admin admin){
        adminRepository.save(admin);
    }

    public void updateAdmin(Admin admin){
        adminRepository.save(admin);
    }
}
