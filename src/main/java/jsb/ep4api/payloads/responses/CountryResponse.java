package jsb.ep4api.payloads.responses;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CountryResponse {
    private Long id;
    private String name;
    private String code;
    private String slug;

    public CountryResponse(Long id, String name, String code, String slug) {
        this.id = id;
        this.name = name;
        this.code = code;
        this.slug = slug;
    }

    public CountryResponse() {
    }
}
