package jsb.ep4api.payloads.requests;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
public class StudioRequest {
    private Long id;
    private String name;
    private String description;
    private MultipartFile logo;
    private MultipartFile banner;
    private String website;
    private String address;
    private Long countryId;
    private Integer establishedYear;
    private String slug;

    public StudioRequest() {
        super();
    }

}
