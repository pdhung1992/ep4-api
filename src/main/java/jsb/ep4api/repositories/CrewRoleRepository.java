package jsb.ep4api.repositories;

import jsb.ep4api.entities.CrewRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface CrewRoleRepository extends JpaRepository<CrewRole, Long>, JpaSpecificationExecutor<CrewRole> {
}
