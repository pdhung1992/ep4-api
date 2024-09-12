package jsb.ep4api.payloads.requests;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResetPasswordRequest {
    private String email;
    private String newPassword;
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
