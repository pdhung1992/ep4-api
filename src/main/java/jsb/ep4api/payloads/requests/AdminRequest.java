package jsb.ep4api.payloads.requests;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AdminRequest {
    private String username;
    private String fullName;
    private String email;
    private String password;
    private Long roleId;

    public AdminRequest() {
    }

    public AdminRequest(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public AdminRequest(String username, String fullName, String email, String password, Long roleId) {
        this.username = username;
        this.fullName = fullName;
        this.email = email;
        this.password = password;
        this.roleId = roleId;
    }
}
