package jsb.ep4api.payloads.responses;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MovieFileResponse {
    private Long id;
    private String title;
    private String fileName;
    private String thumbnail;

    public MovieFileResponse(Long id, String title, String fileName, String thumbnail) {
        this.id = id;
        this.title = title;
        this.fileName = fileName;
        this.thumbnail = thumbnail;
    }

    public MovieFileResponse() {
    }
}
