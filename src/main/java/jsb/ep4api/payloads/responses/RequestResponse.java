package jsb.ep4api.payloads.responses;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RequestResponse {
    private Integer responseCode;
    private String responseMessage;

    public RequestResponse(Integer responseCode, String responseMessage){
        this.responseCode = responseCode;
        this.responseMessage = responseMessage;
    }

    public RequestResponse() {
    }
}
