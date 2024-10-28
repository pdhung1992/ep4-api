package jsb.ep4api.payloads.requests;

import jakarta.validation.constraints.NotBlank;
import jsb.ep4api.validators.annotations.ValidDescription;
import jsb.ep4api.validators.annotations.ValidEntityName;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RoleRequest {
    @ValidEntityName
    private String name;

    @NotBlank
    private String bsColor;

    @ValidDescription
    private String description;

    public RoleRequest() {
    }
}
