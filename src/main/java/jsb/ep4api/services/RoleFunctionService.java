package jsb.ep4api.services;

import jsb.ep4api.entities.RoleFunction;
import jsb.ep4api.repositories.RoleFunctionRepository;
import jsb.ep4api.specifications.RoleFunctionSpecifications;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoleFunctionService {
    @Autowired
    private RoleFunctionRepository roleFunctionRepository;

    public List<RoleFunction> findFunctionsByRole(Long roleId) {

        Specification<RoleFunction> spec = Specification.where(null);
        spec = spec.and(RoleFunctionSpecifications.hasNoDeleteFlag());
        spec = spec.and(RoleFunctionSpecifications.hasRoleId(roleId));

        return roleFunctionRepository.findAll(spec);
    }

    public void createRoleFunction(RoleFunction roleFunction) {
        roleFunctionRepository.save(roleFunction);
    }

    public void deleteRoleFunction(RoleFunction roleFunction) {
        roleFunction.setDeleteFlag(true);
        roleFunctionRepository.save(roleFunction);
    }
}
