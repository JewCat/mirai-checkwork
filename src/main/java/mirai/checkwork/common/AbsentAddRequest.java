package mirai.checkwork.common;

import lombok.Data;

import java.util.List;

@Data
public class AbsentAddRequest {
    private List<Long> absentFirstShift;
    private List<Long> absentSecondShift;
    private List<Long> absentAllDay;
}
