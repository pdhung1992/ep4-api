package jsb.ep4api.payloads.responses;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class TransactionResponse {
    private Long id;
    private String code;
    private Double amount;
    private String content;
    private String gateway;
    private int status;
    private Long userId;
    private Boolean isPackage;
    private Long packageId;
    private String packageName;
    private Long movieId;
    private String movieTitle;
    private LocalDateTime createdAt;

    public TransactionResponse(){
        super();
    }
}
