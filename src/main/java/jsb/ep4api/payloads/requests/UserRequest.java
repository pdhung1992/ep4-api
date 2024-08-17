package jsb.ep4api.payloads.requests;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserRequest {
    private String phone;
    private String fullName;
    private String email;
    private String phoneOrEmail;
    private String password;

    public UserRequest() {
    }

    public UserRequest(String phone, String fullName, String email, String password) {
        this.phone = phone;
        this.fullName = fullName;
        this.email = email;
        this.password = password;
    }

    public UserRequest(String phoneOrEmail, String password) {
        this.phoneOrEmail = phoneOrEmail;
        this.password = password;
    }
}
