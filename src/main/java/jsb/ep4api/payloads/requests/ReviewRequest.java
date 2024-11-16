package jsb.ep4api.payloads.requests;

import jakarta.validation.constraints.Positive;
import jsb.ep4api.validators.annotations.ValidDescription;
import jsb.ep4api.validators.annotations.ValidEntityName;
import jsb.ep4api.validators.annotations.ValidId;
import jsb.ep4api.validators.annotations.ValidRating;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReviewRequest {

    private String title;

    @ValidDescription
    private String content;

    private Long parentId;

    @ValidId
    private Long movieId;


    private Long userId;

    public ReviewRequest() {
    }
}
