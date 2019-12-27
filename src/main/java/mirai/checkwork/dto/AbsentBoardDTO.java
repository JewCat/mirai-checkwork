package mirai.checkwork.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class AbsentBoardDTO {
    private Long absentId;
    private LocalDate absentDate;
    private int absentShift;
    private String userName;
    private Long userId;

    public AbsentBoardDTO(Long absentId, LocalDate absentDate, int absentShift, String userName, Long userId) {
        this.absentId = absentId;
        this.absentDate = absentDate;
        this.absentShift = absentShift;
        this.userName = userName;
        this.userId = userId;
    }
}
