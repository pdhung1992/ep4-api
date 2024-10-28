package jsb.ep4api.payloads.requests;

import jakarta.validation.constraints.NotBlank;
import jsb.ep4api.validators.annotations.ValidDescription;
import jsb.ep4api.validators.annotations.ValidEntityName;
import jsb.ep4api.validators.annotations.ValidSlug;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ClassificationRequest {
    @ValidEntityName
    private String name;

    @ValidDescription
    private String description;

    @ValidSlug
    private String slug;

    @NotBlank
    private String code;

    public ClassificationRequest() {
    }
}
