package jsb.ep4api.payloads.requests;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PackageRequest {
    private Long id;
    private String name;
    private String description;
    private Double price;
    private Integer expirationUnit;
    private String slug;

    public PackageRequest() {
        super();
    }

    public PackageRequest(String name, String description, Double price, Integer expirationUnit, String slug) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.expirationUnit = expirationUnit;
        this.slug = slug;
    }

    public PackageRequest(Long id, String name, String description, Double price, Integer expirationUnit, String slug) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.expirationUnit = expirationUnit;
        this.slug = slug;
    }
}
