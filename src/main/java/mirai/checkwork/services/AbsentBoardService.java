package mirai.checkwork.services;

import mirai.checkwork.common.AbsentRequest;
import mirai.checkwork.models.AbsentBoard;
import mirai.checkwork.models.User;

import java.util.List;

public interface AbsentBoardService {
    User getAuthUser();
    void addAbsent(AbsentRequest req);
    List<AbsentBoard> getListAfterCurrentDateAndUser();
    List<AbsentBoard> getListAfterCurrentDateAndUser(int limit);
    void removeAbsent(Long absentId);
}
