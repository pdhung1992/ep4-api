package jsb.ep4api.repositories;

import jsb.ep4api.entities.CrewDepartment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface CrewDepartmentRepository extends JpaRepository<CrewDepartment, Long>, JpaSpecificationExecutor<CrewDepartment> {
}
