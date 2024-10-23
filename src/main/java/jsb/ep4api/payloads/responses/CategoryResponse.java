package jsb.ep4api.payloads.responses;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CategoryResponse {
    private Long id;
    private String name;
    private String description;
    private String slug;

    public CategoryResponse(){

    }
}
