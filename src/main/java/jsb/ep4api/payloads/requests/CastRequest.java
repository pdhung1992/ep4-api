package jsb.ep4api.payloads.requests;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CastRequest {
    private String actorName;
    private String characterName;

    @JsonProperty("isMain")
    private boolean isMain;

    public CastRequest() {
    }
}
