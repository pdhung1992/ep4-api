package jsb.ep4api.repositories;

import jsb.ep4api.entities.Admin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface AdminRepository extends JpaRepository<Admin, Long>, JpaSpecificationExecutor<Admin> {
    Admin findByUsername(String username);
    Boolean existsByUsername(String username);
    Boolean existsByEmail(String email);
}
