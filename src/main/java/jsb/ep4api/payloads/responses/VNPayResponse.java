package jsb.ep4api.payloads.responses;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class VNPayResponse {
    private String status;
    private String message;
    private String url;
}
