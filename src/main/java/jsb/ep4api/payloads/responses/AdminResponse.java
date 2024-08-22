package jsb.ep4api.payloads.responses;

import jsb.ep4api.entities.Role;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AdminResponse {
    private Long id;
    private String username;
    private String fullName;
    private String email;
    private String avatar;
    private String roleName;
    private Boolean status;
    private String createdAt;
    private String updatedAt;
    private RoleResponse role;
    private int responseCode;
    private String message;

    public AdminResponse(Long id, String username, String fullName, String email, String avatar, RoleResponse roleResponse) {
        this.id = id;
        this.username = username;
        this.fullName = fullName;
        this.email = email;
        this.avatar = avatar;
        this.role = roleResponse;
    }

    public AdminResponse(int responseCode, String message) {
        this.responseCode = responseCode;
        this.message = message;
    }

    public AdminResponse(int responseCode, String message, String avatar) {
        this.responseCode = responseCode;
        this.message = message;
        this.avatar = avatar;
    }
}
