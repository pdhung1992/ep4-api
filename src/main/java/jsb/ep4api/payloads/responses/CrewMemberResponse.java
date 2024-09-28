package jsb.ep4api.payloads.responses;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CrewMemberResponse {
    private Long id;
    private String name;
    private Long crewPositionId;
    private String crewPosition;

    public CrewMemberResponse(Long id, String name, Long crewPositionId, String crewPosition) {
        this.id = id;
        this.name = name;
        this.crewPositionId = crewPositionId;
        this.crewPosition = crewPosition;
    }

    public CrewMemberResponse() {
    }
}
