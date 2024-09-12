package jsb.ep4api.services;

import jsb.ep4api.entities.AdminResetPasswordToken;
import jsb.ep4api.repositories.AdminResetPasswordTokenRepository;
import org.springframework.beans.factory.annotation.Autowired;
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

        Optional<AdminResetPasswordToken> optionalToken = adminResetPasswordTokenRepository.findByToken(token);

        if (optionalToken.isPresent()) {
            AdminResetPasswordToken tokenEntity = optionalToken.get();

            boolean isNotUsed = !tokenEntity.isUsed();
            boolean isNotExpired = tokenEntity.getExpiredAt().isAfter(LocalDateTime.now());
            boolean hasNoDeletedFlag = !tokenEntity.getDeleteFlag();

            if (isNotUsed && isNotExpired && hasNoDeletedFlag) {
                return tokenEntity;
            }
        }

        return null;
    }

    public void saveAdminResetPasswordToken(AdminResetPasswordToken adminResetPasswordToken) {
        adminResetPasswordTokenRepository.save(adminResetPasswordToken);
    }

    public void deleteAdminResetPasswordToken(AdminResetPasswordToken adminResetPasswordToken) {
        adminResetPasswordTokenRepository.delete(adminResetPasswordToken);
    }
}
