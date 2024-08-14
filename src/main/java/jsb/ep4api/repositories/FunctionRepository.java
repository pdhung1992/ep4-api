package jsb.ep4api.repositories;

import jsb.ep4api.entities.Function;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface FunctionRepository extends JpaRepository<Function, Long>, JpaSpecificationExecutor<Function> {

}
