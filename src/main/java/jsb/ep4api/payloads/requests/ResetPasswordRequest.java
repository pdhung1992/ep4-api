package jsb.ep4api.payloads.requests;

import jsb.ep4api.validators.annotations.ValidEmail;
import jsb.ep4api.validators.annotations.ValidPassword;
import jsb.ep4api.validators.annotations.ValidToken;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResetPasswordRequest {
    @ValidEmail
    private String email;

    @ValidPassword
    private String newPassword;

    @ValidToken
    private String token;

    public ResetPasswordRequest() {
    }

    public ResetPasswordRequest( String newPassword, String token) {
        this.newPassword = newPassword;
        this.token = token;
    }

    public ResetPasswordRequest(String email){
        this.email = email;
    }
}
