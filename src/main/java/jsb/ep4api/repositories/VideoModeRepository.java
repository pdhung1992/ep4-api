package jsb.ep4api.repositories;

import jsb.ep4api.entities.VideoMode;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface VideoModeRepository extends JpaRepository<VideoMode, Long>, JpaSpecificationExecutor<VideoMode> {

}
