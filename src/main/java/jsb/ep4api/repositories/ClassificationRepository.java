package jsb.ep4api.repositories;

import jsb.ep4api.entities.Classification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface ClassificationRepository extends JpaRepository<Classification, Long>, JpaSpecificationExecutor<Classification> {
}