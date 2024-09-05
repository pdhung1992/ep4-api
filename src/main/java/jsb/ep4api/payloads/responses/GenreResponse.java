package jsb.ep4api.payloads.responses;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GenreResponse {
    private Long id;
    private String name;
    private String description;
    private String slug;

    public GenreResponse() {
    }

    public GenreResponse(Long id, String name, String description, String slug) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.slug = slug;
    }
}
