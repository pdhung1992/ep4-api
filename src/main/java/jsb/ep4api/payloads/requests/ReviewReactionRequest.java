package jsb.ep4api.payloads.requests;

import jakarta.validation.constraints.NotNull;
import jsb.ep4api.validators.annotations.ValidId;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReviewReactionRequest {

    private Long reviewId;


    private Long userId;

    @NotNull
    private boolean reactionType;

    public ReviewReactionRequest() {
    }
}
