package jsb.ep4api.payloads.requests;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RatingRequest {
    private Integer value;
    private Long userId;
    private Long movieId;

    public RatingRequest() {
    }
}
