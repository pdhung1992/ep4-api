package jsb.ep4api.repositories;

import jsb.ep4api.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long>, JpaSpecificationExecutor<User> {

    @Query("SELECT u FROM User u WHERE u.phone = :phoneOrEmail OR u.email = :phoneOrEmail")
    Optional<User> findByPhoneOrEmail(@Param("phoneOrEmail") String phoneOrEmail);

    Optional<User> findByEmail(String email);

    Boolean existsByPhone(String phone);
    Boolean existsByEmail(String email);
}
