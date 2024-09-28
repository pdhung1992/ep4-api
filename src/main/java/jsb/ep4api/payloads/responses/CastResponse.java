package jsb.ep4api.payloads.responses;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CastResponse {
    private Long id;
    private String actorName;
    private String characterName;
    private boolean isMain;

    public CastResponse(Long id, String actorName, String characterName, boolean isMain) {
        this.id = id;
        this.actorName = actorName;
        this.characterName = characterName;
        this.isMain = isMain;
    }

    public CastResponse() {
    }
}
