package jsb.ep4api.payloads.responses;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StudioResponse {
    private Long id;
    private String name;
    private String description;
    private String logo;
    private String banner;
    private String website;
    private String address;
    private String country;
    private Long countryId;
    private Integer establishedYear;
    private String slug;
    private Boolean deleteFlag;
    private String createdAt;
    private String modifiedAt;

    public StudioResponse() {
    }
}
