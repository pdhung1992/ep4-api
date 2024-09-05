package jsb.ep4api.payloads.responses;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CrewPositionResponse {
    private Long id;
    private String name;
    private String description;

    public CrewPositionResponse() {
    }

    public CrewPositionResponse(Long id, String name, String description) {
        this.id = id;
        this.name = name;
        this.description = description;
    }
}
