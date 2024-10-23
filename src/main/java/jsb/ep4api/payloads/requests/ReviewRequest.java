package jsb.ep4api.payloads.requests;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReviewRequest {
    private String title;
    private String content;
    private int rating;
    private Long parentId;
    private Long movieId;
    private Long userId;

    public ReviewRequest() {
    }
}
