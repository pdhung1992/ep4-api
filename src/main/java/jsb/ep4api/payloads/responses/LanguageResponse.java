package jsb.ep4api.payloads.responses;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LanguageResponse {
    private Long id;
    private String name;
    private String nativeName;
    private String code;
    private String slug;

    public LanguageResponse(Long id, String name, String nativeName, String code, String slug) {
        this.id = id;
        this.name = name;
        this.nativeName = nativeName;
        this.code = code;
        this.slug = slug;
    }

    public LanguageResponse() {
    }
}
