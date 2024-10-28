package jsb.ep4api.payloads.requests;

import jakarta.validation.constraints.NotNull;
import jsb.ep4api.validators.annotations.ValidEntityName;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
public class MovieFileRequest {
    @NotNull
    private MultipartFile file;

    @NotNull
    private MultipartFile thumbnail;

    @ValidEntityName
    private String title;

    public MovieFileRequest() {
    }
}
