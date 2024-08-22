package jsb.ep4api.payloads.responses;

import jsb.ep4api.entities.Function;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RoleResponse {
    private Long id;
    private String name;
    private String slug;
    private String bsColor;
    private String description;
    private Boolean deleteFlag;
    private String createdAt;
    private String modifiedAt;
    private Function[] functions;
    private int responseCode;
    private String message;

    public RoleResponse(Long id, String name, String slug, String bsColor, String description, Boolean deleteFlag, String createdAt, String modifiedAt) {
        this.id = id;
        this.name = name;
        this.slug = slug;
        this.bsColor = bsColor;
        this.description = description;
        this.deleteFlag = deleteFlag;
        this.createdAt = createdAt;
        this.modifiedAt = modifiedAt;
    }

    public RoleResponse(Long id, String name, String slug, String bsColor) {
        this.id = id;
        this.name = name;
        this.slug = slug;
        this.bsColor = bsColor;
    }

    public RoleResponse(Long id, String name, String bsColor, String description, String slug) {
        this.id = id;
        this.name = name;
        this.bsColor = bsColor;
        this.description = description;
        this.slug = slug;
    }

    public RoleResponse(int responseCode, String message) {
        this.responseCode = responseCode;
        this.message = message;
    }

    public RoleResponse() {
        super();
    }
}
