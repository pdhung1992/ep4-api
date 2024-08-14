package jsb.ep4api.repositories;

import jsb.ep4api.entities.RoleFunction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleFunctionRepository extends JpaRepository<RoleFunction, Long>, JpaSpecificationExecutor<RoleFunction> {

}
