package jsb.ep4api.repositories;

import jsb.ep4api.entities.UserMovie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface UserMovieRepository extends JpaRepository<UserMovie, Long>, JpaSpecificationExecutor<UserMovie> {
}
