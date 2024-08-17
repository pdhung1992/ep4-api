package jsb.ep4api.repositories;

import jsb.ep4api.entities.MovieLanguage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface MovieLanguageRepository extends JpaRepository<MovieLanguage, Long>, JpaSpecificationExecutor<MovieLanguage> {

}