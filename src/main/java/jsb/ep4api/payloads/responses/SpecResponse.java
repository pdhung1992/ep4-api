package jsb.ep4api.payloads.responses;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SpecResponse {
    private int currentPage;
    private int totalPages;
    private long totalItems;
    private long fromItem;
    private long toItem;
    private int pageSize;
    private String sortField;
    private String sortDir;
    private Object data;
}
