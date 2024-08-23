package jsb.ep4api.payloads.requests;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;

@Getter
@Setter
public class UserRequest {
    private Long id;
    private String phone;
    private String fullName;
    private String email;
    private String phoneOrEmail;
    private String password;
    private String newPassword;
    private MultipartFile avatar;

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

    public UserRequest(Long id, String password, String newPassword) {
        this.id = id;
        this.password = password;
        this.newPassword = newPassword;
    }

    public UserRequest(Long id, MultipartFile avatar) {
        this.id = id;
        this.avatar = avatar;
    }
}
