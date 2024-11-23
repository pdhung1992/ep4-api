package jsb.ep4api.payloads.responses;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class UserMovieResponse {
    private Long id;
    private Long movieId;
    private String movieTitle;
    private Double moviePrice;
    private String slug;
    private LocalDateTime expiredAt;
}
