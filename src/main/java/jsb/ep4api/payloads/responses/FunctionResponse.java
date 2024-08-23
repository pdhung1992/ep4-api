package jsb.ep4api.payloads.responses;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class FunctionResponse {
    private Long id;
    private String name;
    private String slug;
    private String icon;
    private String description;
    private int sortOrder;
    private List<FunctionResponse> childFunctions;

    public FunctionResponse() {
        super();
    }

    public FunctionResponse(Long id, String name, String description, String slug, String icon, int sortOrder, List<FunctionResponse> childFunctions) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.slug = slug;
        this.icon = icon;
        this.sortOrder = sortOrder;
        this.childFunctions = childFunctions;
    }

    public FunctionResponse(Long id, String name, String description, String slug, String icon, int sortOrder) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.slug = slug;
        this.icon = icon;
        this.sortOrder = sortOrder;
    }
}
