package jsb.ep4api.payloads.requests;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CrewMemberRequest {
    private String name;
    private Long crewPositionId;

    public CrewMemberRequest() {
    }
}
