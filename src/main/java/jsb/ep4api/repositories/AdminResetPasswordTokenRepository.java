package jsb.ep4api.repositories;


import jsb.ep4api.entities.AdminResetPasswordToken;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AdminResetPasswordTokenRepository extends JpaRepository<AdminResetPasswordToken, Long>, JpaSpecificationExecutor<AdminResetPasswordToken> {
    Optional<AdminResetPasswordToken> findByToken(String token);
    Optional<AdminResetPasswordToken> findByAdminId(long adminId);
}
