package jsb.ep4api.payloads.requests;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TransactionRequest {
    private String code;
    private Double amount;
    private String content;
    private String gateway;
    private int status;
    private Long userId;
    private Boolean isPackage;
    private Long packageId;
    private Long movieId;

    public TransactionRequest() {
    }
}
