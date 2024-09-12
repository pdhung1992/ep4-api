package jsb.ep4api.payloads.requests;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;


@Getter
@Setter
public class AdminRequest {
    private Long id;
    private String username;
    private String fullName;
    private String email;
    private String password;
    private String newPassword;
    private String token;
    private Long roleId;
    MultipartFile avatar;

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

    public AdminRequest(Long id, String fullName, String email, Long roleId) {
        this.id = id;
        this.fullName = fullName;
        this.email = email;
        this.roleId = roleId;
    }

    public AdminRequest(Long id, String password, String newPassword) {
        this.id = id;
        this.password = password;
        this.newPassword = newPassword;
    }

    public AdminRequest(Long id, MultipartFile avatar) {
        this.id = id;
        this.avatar = avatar;
    }
}
