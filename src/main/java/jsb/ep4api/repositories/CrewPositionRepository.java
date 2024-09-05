package jsb.ep4api.repositories;

import jsb.ep4api.entities.CrewPosition;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface CrewPositionRepository extends JpaRepository<CrewPosition, Long>, JpaSpecificationExecutor<CrewPosition> {
}
