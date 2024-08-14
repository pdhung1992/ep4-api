package jsb.ep4api.repositories;

import jsb.ep4api.entities.Cast;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface CastRepository extends JpaRepository<Cast, Long>, JpaSpecificationExecutor<Cast> {

}
