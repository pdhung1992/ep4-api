package jsb.ep4api.repositories;

import jsb.ep4api.entities.MovieCast;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface MovieCastRepository extends JpaRepository<MovieCast, Long>, JpaSpecificationExecutor<MovieCast> {
}
