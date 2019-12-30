package mirai.checkwork.common;

import lombok.Data;

@Data
public class CheckEditRequest {
    private Long checkId;
    private Long checkInTime;
    private Long checkOutTime;
}
