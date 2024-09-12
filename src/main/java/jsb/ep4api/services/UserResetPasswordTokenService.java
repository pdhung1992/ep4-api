package jsb.ep4api.services;

import jsb.ep4api.entities.UserResetPasswordToken;
import jsb.ep4api.repositories.UserResetPasswordTokenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class UserResetPasswordTokenService {
    @Autowired
    private UserResetPasswordTokenRepository userResetPasswordTokenRepository;

    public UserResetPasswordToken getUserResetPasswordTokenByUserId(long userId) {
        return userResetPasswordTokenRepository.findByUserId(userId).orElse(null);
    }

    public UserResetPasswordToken getUserResetPasswordTokenByToken(String token) {

        Optional<UserResetPasswordToken> optionalToken = userResetPasswordTokenRepository.findByToken(token);

        if (optionalToken.isPresent()) {
            UserResetPasswordToken tokenEntity = optionalToken.get();

            boolean isNotUsed = !tokenEntity.isUsed();
            boolean isNotExpired = tokenEntity.getExpiredAt().isAfter(LocalDateTime.now());
            boolean hasNoDeletedFlag = !tokenEntity.getDeleteFlag();

            if (isNotUsed && isNotExpired && hasNoDeletedFlag) {
                return tokenEntity;
            }
        }

        return null;
    }

    public void saveUserResetPasswordToken(UserResetPasswordToken userResetPasswordToken) {
        userResetPasswordTokenRepository.save(userResetPasswordToken);
    }

    public void deleteUserResetPasswordToken(UserResetPasswordToken userResetPasswordToken) {
        userResetPasswordTokenRepository.delete(userResetPasswordToken);
    }
}
