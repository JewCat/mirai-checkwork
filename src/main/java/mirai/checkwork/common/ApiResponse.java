package mirai.checkwork.common;

import lombok.Data;

@Data
public class ApiResponse {
    private int httpStatus;
    private Object data;
    private String message;
}
