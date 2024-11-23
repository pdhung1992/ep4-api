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
    private Boolean isActive;
    private String joinDate;
    private String avatar;

    public UserResponse() {
        super();
    }
}
