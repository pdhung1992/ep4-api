package jsb.ep4api.repositories;

import jsb.ep4api.entities.ReviewReaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface ReviewReactionRepository extends JpaRepository<ReviewReaction, Long>, JpaSpecificationExecutor<ReviewReaction> {

}
