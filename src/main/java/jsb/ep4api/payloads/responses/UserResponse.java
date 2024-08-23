package jsb.ep4api.payloads.responses;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserResponse {
    private Long id;
    private String fullName;
    private String phone;
    private String email;
    private String avatar;
    private Boolean verifyFlag;
    private Boolean deleteFlag;
    private String createdAt;
    private String modifiedAt;
    private int responseCode;
    private String message;

    public UserResponse(Long id, String fullName, String phone, String email, String avatar, Boolean verifyFlag, Boolean deleteFlag, String createdAt, String modifiedAt) {
        this.id = id;
        this.fullName = fullName;
        this.phone = phone;
        this.email = email;
        this.avatar = avatar;
        this.verifyFlag = verifyFlag;
        this.deleteFlag = deleteFlag;
        this.createdAt = createdAt;
        this.modifiedAt = modifiedAt;
    }

    public UserResponse(int responseCode, String message) {
        this.responseCode = responseCode;
        this.message = message;
    }


    public UserResponse(int responseCode, String message, String avatar) {
        this.responseCode = responseCode;
        this.message = message;
        this.avatar = avatar;
    }
}
