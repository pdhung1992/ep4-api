package jsb.ep4api.services;

import jsb.ep4api.entities.AdminResetPasswordToken;
import jsb.ep4api.repositories.AdminResetPasswordTokenRepository;
import jsb.ep4api.specifications.AdminResetPasswordTokenSpecifications;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class AdminResetPasswordTokenService {
    @Autowired
    private AdminResetPasswordTokenRepository adminResetPasswordTokenRepository;

    public AdminResetPasswordToken getAdminResetPasswordTokenByAdminId(long adminId) {
        return adminResetPasswordTokenRepository.findByAdminId(adminId).orElse(null);
    }

    public AdminResetPasswordToken getAdminResetPasswordTokenByToken(String token) {

        Specification<AdminResetPasswordToken> spec = Specification.where(null);
        spec = spec.and(AdminResetPasswordTokenSpecifications.hasToken(token));
        spec = spec.and(AdminResetPasswordTokenSpecifications.hasNotUsed());
        spec = spec.and(AdminResetPasswordTokenSpecifications.hasNoDeleteFlag());
        spec = spec.and(AdminResetPasswordTokenSpecifications.hasNotExpired());

        Optional<AdminResetPasswordToken> optionalToken = adminResetPasswordTokenRepository.findOne(spec);

        return optionalToken.orElse(null);
    }

    public void saveAdminResetPasswordToken(AdminResetPasswordToken adminResetPasswordToken) {
        adminResetPasswordTokenRepository.save(adminResetPasswordToken);
    }

    public void deleteAdminResetPasswordToken(AdminResetPasswordToken adminResetPasswordToken) {
        adminResetPasswordTokenRepository.delete(adminResetPasswordToken);
    }
}
