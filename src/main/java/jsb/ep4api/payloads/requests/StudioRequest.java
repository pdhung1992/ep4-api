package jsb.ep4api.payloads.requests;

import jakarta.validation.constraints.NotBlank;
import jsb.ep4api.validators.annotations.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
public class StudioRequest {
    @ValidId
    private Long id;

    @ValidFullName
    private String name;

    @ValidDescription
    private String description;

    private MultipartFile logo;
    private MultipartFile banner;

    @NotBlank
    private String website;

    @NotBlank
    private String address;

    @ValidId
    private Long countryId;

    @ValidYearToPresent
    private Integer establishedYear;

    @ValidSlug
    private String slug;

    public StudioRequest() {
        super();
    }

}
