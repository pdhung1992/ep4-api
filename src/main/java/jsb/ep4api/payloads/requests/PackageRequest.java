package jsb.ep4api.payloads.requests;

import jakarta.validation.constraints.Positive;
import jsb.ep4api.validators.annotations.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PackageRequest {
    @ValidId
    private Long id;

    @ValidEntityName
    private String name;

    @ValidDescription
    private String description;

    @ValidCurrency
    private Double price;

    @Positive
    private Integer expirationUnit;

    @ValidSlug
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
