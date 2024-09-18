package jsb.ep4api.payloads.responses;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class PackageResponse {
    private Long id;
    private String name;
    private String description;
    private Double price;
    private Integer expirationUnit;
    private String slug;

    public PackageResponse() {
        super();
    }
}
