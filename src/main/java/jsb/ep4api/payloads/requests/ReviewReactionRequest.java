package jsb.ep4api.payloads.requests;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReviewReactionRequest {
    private Long reviewReactionId;
    private Long reviewId;
    private Long userId;
    private boolean reactionType;

    public ReviewReactionRequest() {
    }
}
