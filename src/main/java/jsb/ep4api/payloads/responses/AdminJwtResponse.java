package jsb.ep4api.payloads.responses;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AdminJwtResponse {
    private String token;
    private Long id;
    private String username;
    private String email;
    private String fullName;
    private String avatar;
    private String role;

    public AdminJwtResponse(String token, Long id, String username, String email, String fullName, String avatar, String role) {
        this.token = token;
        this.id = id;
        this.username = username;
        this.email = email;
        this.fullName = fullName;
        this.avatar = avatar;
        this.role = role;
    }

    public AdminJwtResponse() {
    }
}
