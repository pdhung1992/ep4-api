package jsb.ep4api.payloads.responses;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class UserPackageResponse {
    private Long id;
    private Long packageId;
    private String packageName;
    private Double packagePrice;
    private String slug;
    private LocalDateTime expiredAt;
}
