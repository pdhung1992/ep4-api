package jsb.ep4api.payloads.responses;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ClassificationResponse {
    private Long id;
    private String name;
    private String description;
    private String code;
    private String slug;

    public ClassificationResponse(Long id, String name, String description, String code, String slug) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.code = code;
        this.slug = slug;
    }

    public ClassificationResponse() {
    }
}
