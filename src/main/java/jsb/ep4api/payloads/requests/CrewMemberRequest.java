package jsb.ep4api.payloads.requests;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jsb.ep4api.validators.annotations.ValidFullName;
import jsb.ep4api.validators.annotations.ValidId;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CrewMemberRequest {
    @ValidFullName
    private String name;

    @ValidId
    private Long crewPositionId;

    public CrewMemberRequest() {
    }
}
