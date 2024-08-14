package jsb.ep4api.payloads.responses;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserJwtResponse {
    private String token;
    private Long id;
    private String phone;
    private String email;
    private String fullName;
    private String avatar;

    public UserJwtResponse(String token, Long id, String phone, String email, String fullName, String avatar) {
        this.token = token;
        this.id = id;
        this.phone = phone;
        this.email = email;
        this.fullName = fullName;
        this.avatar = avatar;
    }

}
