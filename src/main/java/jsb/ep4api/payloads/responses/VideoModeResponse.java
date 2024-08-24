package jsb.ep4api.payloads.responses;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class VideoModeResponse {
    private Long id;
    private String name;
    private String description;
    private String code;
    private String resolution;
    private String slug;

    public VideoModeResponse() {
    }

    public VideoModeResponse(Long id, String name, String description, String code, String resolution, String slug) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.code = code;
        this.resolution = resolution;
        this.slug = slug;
    }
}
