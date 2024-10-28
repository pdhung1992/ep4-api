package jsb.ep4api.payloads.requests;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class VNPayRequest {
    private Double amount;
    private String bankCode;
    private String locale;
    private Boolean isPackage;
    private Long packageId;
    private Long movieId;

    public VNPayRequest() {
    }
}
