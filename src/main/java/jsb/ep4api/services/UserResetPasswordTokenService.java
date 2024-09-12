package jsb.ep4api.services;

import jsb.ep4api.entities.UserResetPasswordToken;
import jsb.ep4api.repositories.UserResetPasswordTokenRepository;
import jsb.ep4api.specifications.UserResetPasswordTokenSpecifications;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
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

        Specification<UserResetPasswordToken> spec = Specification.where(null);
        spec = spec.and(UserResetPasswordTokenSpecifications.hasNoDeleteFlag());
        spec = spec.and(UserResetPasswordTokenSpecifications.hasNotUsed());
        spec = spec.and(UserResetPasswordTokenSpecifications.hasNotExpired());
        spec = spec.and(UserResetPasswordTokenSpecifications.hasToken(token));

        Optional<UserResetPasswordToken> optionalToken = userResetPasswordTokenRepository.findOne(spec);

        return optionalToken.orElse(null);
    }

    public void saveUserResetPasswordToken(UserResetPasswordToken userResetPasswordToken) {
        userResetPasswordTokenRepository.save(userResetPasswordToken);
    }

    public void deleteUserResetPasswordToken(UserResetPasswordToken userResetPasswordToken) {
        userResetPasswordTokenRepository.delete(userResetPasswordToken);
    }
}
