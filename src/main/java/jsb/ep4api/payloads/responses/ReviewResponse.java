package jsb.ep4api.payloads.responses;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Date;

@Getter
@Setter
public class ReviewResponse {
    private Long id;
    private String title;
    private String content;
    private int rating;
    private Long parentId;
    private Long movieId;
    private Long userId;
    private String userName;
    private Long likeCount;
    private Long dislikeCount;
    private Long replyCount;
    private String replyTo;
    private Boolean userReaction;
    private String createdAt;

    public ReviewResponse() {
    }
}
