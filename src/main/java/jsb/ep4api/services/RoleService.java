package jsb.ep4api.services;

import jsb.ep4api.entities.Role;
import jsb.ep4api.repositories.RoleRepository;

import jsb.ep4api.specifications.RoleSpecifications;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoleService {
    @Autowired
    private RoleRepository roleRepository;

    public Page<Role> getAllRoles(
            int pageNo,
            int pageSize,
            String sortField,
            String sortDir
    ) {
        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ?
                Sort.by(sortField).ascending() :
                Sort.by(sortField).descending();
        Pageable pageable = PageRequest.of(pageNo - 1, pageSize, sort);

        Specification<Role> spec = Specification.where(null);
        spec = spec.and(RoleSpecifications.hasNotDeleteFlag());

        return roleRepository.findAll(spec, pageable);
    }

    public List<Role> getRoleChoices() {
        Specification<Role> spec = Specification.where(null);
        spec = spec.and(RoleSpecifications.hasNotDeleteFlag());
        return roleRepository.findAll(spec);
    }

    public Role findRoleById(Long id) {
        return roleRepository.findById(id).orElse(null);
    }

    public Boolean existsByName(String name) {
        return roleRepository.existsByName(name);
    }

    public Boolean existsBySlug(String slug) {
        return roleRepository.existsBySlug(slug);
    }

    public Role createRole(Role role) {
        return roleRepository.save(role);
    }

    public Role updateRole(Role role) {
        return roleRepository.save(role);
    }

}
