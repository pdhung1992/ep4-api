package jsb.ep4api.payloads.requests;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import jsb.ep4api.validators.annotations.ValidFullName;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CastRequest {
    @ValidFullName
    private String actorName;

    @ValidFullName
    private String characterName;

    @NotNull
    @JsonProperty("isMain")
    private boolean isMain;

    public CastRequest() {
    }
}
