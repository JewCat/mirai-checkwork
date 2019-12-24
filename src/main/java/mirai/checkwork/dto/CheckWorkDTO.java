package mirai.checkwork.dto;

import lombok.Data;

import javax.persistence.Column;
import java.sql.Date;
import java.sql.Time;

@Data
public class CheckWorkDTO {
    private Long userId;
    private String userName;
    private Date checkDate;
    private Time checkInTime;
    private Time checkOutTime;
    private Long absentShifts;

    public CheckWorkDTO(Long userId, String userName, Date checkDate, Time checkInTime, Time checkOutTime, Long absentShifts) {
        this.userId = userId;
        this.userName = userName;
        this.checkDate = checkDate;
        this.checkInTime = checkInTime;
        this.checkOutTime = checkOutTime;
        this.absentShifts = absentShifts;
    }
}
