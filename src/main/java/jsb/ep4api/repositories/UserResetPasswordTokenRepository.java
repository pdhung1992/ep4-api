package jsb.ep4api.repositories;

import jsb.ep4api.entities.UserResetPasswordToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserResetPasswordTokenRepository extends JpaRepository<UserResetPasswordToken, Long>, JpaSpecificationExecutor<UserResetPasswordToken> {
    Optional<UserResetPasswordToken> findByToken(String token);
    Optional<UserResetPasswordToken> findByUserId(long userId);
}
