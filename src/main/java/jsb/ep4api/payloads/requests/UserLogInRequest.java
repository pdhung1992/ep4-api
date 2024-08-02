package jsb.ep4api.payloads.requests;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserLogInRequest {
    private String usernameOrEmail;
    private String password;

    public UserLogInRequest() {
    }

    public UserLogInRequest(String usernameOrEmail, String password) {
        this.usernameOrEmail = usernameOrEmail;
        this.password = password;
    }
}
