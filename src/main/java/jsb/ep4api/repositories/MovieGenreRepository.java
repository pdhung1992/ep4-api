package jsb.ep4api.repositories;

import jsb.ep4api.entities.MovieGenre;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface MovieGenreRepository extends JpaRepository<MovieGenre, Long>, JpaSpecificationExecutor<MovieGenre> {

}