package jsb.ep4api.services;

import jsb.ep4api.entities.Role;
import jsb.ep4api.repositories.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RoleService {
    @Autowired
    private RoleRepository roleRepository;

    public Role findRoleById(Long id){
        return roleRepository.findById(id).orElse(null);
    }
}
