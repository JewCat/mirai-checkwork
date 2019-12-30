package mirai.checkwork.services;

import mirai.checkwork.common.AbsentAddRequest;
import mirai.checkwork.dto.AbsentBoardDTO;
import mirai.checkwork.models.AbsentBoard;
import mirai.checkwork.models.User;

import java.time.LocalDate;
import java.util.List;

public interface AbsentBoardService {
    User getAuthUser();
    void addAbsent(AbsentAddRequest req);
    List<AbsentBoard> getListAfterCurrentDateAndUser();
    List<AbsentBoard> getListAfterCurrentDateAndUser(int limit);
    void removeAbsent(Long absentId);
    List<AbsentBoardDTO> getListAbsentBoardAdmin(LocalDate date);
}
