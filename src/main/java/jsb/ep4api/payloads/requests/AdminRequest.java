package jsb.ep4api.payloads.requests;

import jsb.ep4api.validators.annotations.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;


@Getter
@Setter
public class AdminRequest {
    @ValidId
    private Long id;

    @ValidUsername
    private String username;

    @ValidFullName
    private String fullName;

    @ValidEmail
    private String email;

    @ValidPassword
    private String password;

    @ValidPassword
    private String newPassword;

    @ValidToken
    private String token;

    @ValidId
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
