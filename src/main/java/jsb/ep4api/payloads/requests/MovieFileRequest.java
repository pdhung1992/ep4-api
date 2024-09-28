package jsb.ep4api.payloads.requests;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
public class MovieFileRequest {
    MultipartFile file;
    MultipartFile thumbnail;
    String title;

    public MovieFileRequest() {
    }
}
