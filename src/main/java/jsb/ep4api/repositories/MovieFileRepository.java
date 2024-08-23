package jsb.ep4api.repositories;

import jsb.ep4api.entities.MovieFile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface MovieFileRepository extends JpaRepository<MovieFile, Long>, JpaSpecificationExecutor<MovieFile> {
}
