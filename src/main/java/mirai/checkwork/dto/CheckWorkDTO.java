package mirai.checkwork.dto;

import lombok.Data;
import java.time.LocalTime;

@Data
public class CheckWorkDTO {
    private Long userId;
    private String userName;
    private LocalTime checkInTime;
    private LocalTime checkOutTime;
    private Long checkId;

    public CheckWorkDTO() { }

    public CheckWorkDTO(Long userId, String userName, LocalTime checkInTime, LocalTime checkOutTime, Long checkId) {
        this.userId = userId;
        this.userName = userName;
        this.checkInTime = checkInTime;
        this.checkOutTime = checkOutTime;
        this.checkId = checkId;
    }
}
