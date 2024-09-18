package jsb.ep4api.payloads.requests;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ClassificationRequest {
    private String name;
    private String description;
    private String slug;
    private String code;

    public ClassificationRequest() {
    }
}
