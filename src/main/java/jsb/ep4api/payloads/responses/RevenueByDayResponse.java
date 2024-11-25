package jsb.ep4api.payloads.responses;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RevenueByDayResponse {
    private String date;
    private double revenue;
    private int transactions;
}
